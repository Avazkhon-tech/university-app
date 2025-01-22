package uz.mu.lms.service.impl;

import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.LocationDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.exceptions.*;
import uz.mu.lms.model.*;
import uz.mu.lms.model.enums.AttendanceStatus;
import uz.mu.lms.model.redis.AttendanceChecking;
import uz.mu.lms.projection.AttendanceSummaryProjection;
import uz.mu.lms.repository.*;
import uz.mu.lms.repository.redis.AttendanceCheckingRepository;
import uz.mu.lms.service.AttendanceService;
import uz.mu.lms.service.BuildingService;
import uz.mu.lms.service.QRCodeService;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    @Value("${spring.defaultValues.host}")
    private String hostAddr;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;
    private final LessonRepository lessonRepository;
    private final AttendanceCheckingRepository attendanceCheckingRepository;
    private final QRCodeService qrCodeService;
    private final BuildingService buildingService;

    @Override
    public List<AttendanceSummaryProjection> getAttendanceSummaries() {
        Student currentStudent = getCurrentStudent();
        return attendanceRepository.findAttendanceSummaryByCourseForStudent(currentStudent.getId());
    }

    @Override
    public byte[] generateAttendanceQR(Integer lessonId) {
        Lesson lesson = getLessonById(lessonId);
        List<Group> groups = groupRepository.findGroupsByLessonId(lessonId);

        List<Attendance> attendances = initializeAttendances(groups, lesson);
        attendanceRepository.saveAll(attendances);

        UUID attendanceUUID = generateAttendanceUUID();
        System.out.println("UUID: " + attendanceUUID);
        AttendanceChecking attendanceChecking = createAttendanceChecking(attendanceUUID, lessonId);

        return generateQRCode(attendanceChecking.getId());
    }

    @Override
    public void recordAttendance(String qrUUID, LocationDto locationDto) {
        if (!buildingService.isUserWithinUniversityZone(locationDto.latitude(), locationDto.longitude())) {
            throw new StudentIsNotInUniversityZoneException("You are out of the university zone");
        }

        AttendanceChecking attendanceChecking = getAttendanceCheckingByUUID(qrUUID);
        validateLessonExistence(attendanceChecking.getLessonId());

        Student student = getCurrentStudent();
        Attendance attendance = getAttendanceForStudent(student.getId(), attendanceChecking.getLessonId());

        validateAttendanceStatus(attendance);

        attendance.setStatus(AttendanceStatus.PRESENT);
        attendanceRepository.save(attendance);

    }

    private Lesson getLessonById(Integer lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson with id %s not found".formatted(lessonId)));
    }

    private List<Attendance> initializeAttendances(List<Group> groups, Lesson lesson) {
        List<Attendance> attendances = new LinkedList<>();
        groups.forEach(group -> group.getStudents().forEach(student -> {
            if (!attendanceRepository.existsByStudentIdAndLessonId(student.getId(), lesson.getId())) {
                Attendance attendance = Attendance.builder()
                        .course(lesson.getLessonTemplate().getCourse())
                        .student(student)
                        .lesson(lesson)
                        .status(AttendanceStatus.ABSENT)
                        .build();
                attendances.add(attendance);
            }
        }));
        return attendances;
    }

    private UUID generateAttendanceUUID() {
        return UUID.randomUUID();
    }

    private AttendanceChecking createAttendanceChecking(UUID uuid, Integer lessonId) {
        AttendanceChecking entity = new AttendanceChecking(uuid, lessonId);
        return attendanceCheckingRepository.save(entity);
    }

    private byte[] generateQRCode(UUID attendanceCheckingId) {
        try {
            String qrCodeUrl = hostAddr + "/api/attendance/" + attendanceCheckingId;
            return qrCodeService.generateQRCode(qrCodeUrl);
        } catch (IOException | WriterException e) {
            throw new CouldNotProcessRequestException("QR code generation failed");
        }
    }

    private AttendanceChecking getAttendanceCheckingByUUID(String qrUUID) {
        return attendanceCheckingRepository.findById(UUID.fromString(qrUUID))
                .orElseThrow(() -> new ResourceNotFoundException("QR code is expired"));
    }

    private void validateLessonExistence(Integer lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new ResourceNotFoundException("Lesson with id %s not found".formatted(lessonId));
        }
    }

    private Attendance getAttendanceForStudent(Integer studentId, Integer lessonId) {
        return attendanceRepository.findAttendanceByStudentIdAndLessonId(studentId, lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("You do not belong to this group"));
    }

    private void validateAttendanceStatus(Attendance attendance) {
        AttendanceStatus status = attendance.getStatus();
        if(status != AttendanceStatus.ABSENT) {
            throw new ResourceAlreadyExistsException("Your attendance status has already been recorded as %s"
                    .formatted(status.toString().toLowerCase()));
        }
    }


    // Finds the current student from security context
    private Student getCurrentStudent() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return studentRepository.findByUser_Username(username)
                .orElseThrow(() -> new UserNotFoundException("Student not found"));
    }
}

