package uz.mu.lms.service.impl;

import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.exceptions.CouldNotProcessRequestException;
import uz.mu.lms.exceptions.ResourceAlreadyExistsException;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.exceptions.UserNotFoundException;
import uz.mu.lms.model.*;
import uz.mu.lms.model.enums.AttendanceStatus;
import uz.mu.lms.model.redis.AttendanceChecking;
import uz.mu.lms.repository.AttendanceRepository;
import uz.mu.lms.repository.LessonRepository;
import uz.mu.lms.repository.LessonTemplateRepository;
import uz.mu.lms.repository.StudentRepository;
import uz.mu.lms.repository.redis.AttendanceCheckingRepository;
import uz.mu.lms.service.AttendanceService;
import uz.mu.lms.service.QRCodeService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {


    @Value("${spring.defaultValues.host}")
    private String hostAddr;
    private final StudentRepository studentRepository;
    private final LessonTemplateRepository lessonTemplateRepository;
    private final AttendanceRepository attendanceRepository;
    private final LessonRepository lessonRepository;
    private final AttendanceCheckingRepository attendanceCheckingRepository;
    private final QRCodeService qrCodeService;

    @Override
    public byte[] generateAttendanceQR(Integer lessonId) {

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson with id %s not found"
                        .formatted(lessonId)));

        LessonTemplate lessonTemplate = lesson.getLessonTemplate();

        // todo
//        for (Student student : lessonTemplate.getGroup().getStudents()) {
//            if (!attendanceRepository.existsByStudentIdAndLessonId(student.getId(), lessonId)) {
//                Attendance attendance = Attendance
//                        .builder()
//                        .course(lesson.getLessonTemplate().getCourse())
//                        .student(student)
//                        .lesson(lesson)
//                        .status(AttendanceStatus.ABSENT)
//                        .build();
//                attendanceRepository.save(attendance);
//            }
//        }

        UUID uuid = UUID.randomUUID();
        System.out.println("---- " + uuid + " ----");
        AttendanceChecking entity = new AttendanceChecking(uuid, lessonId);
        AttendanceChecking attendanceChecking = attendanceCheckingRepository.save(entity);


        byte[] bytes = null;
        try {
            bytes = qrCodeService.generateQRCode(hostAddr + "/api/" + attendanceChecking.getId());
        } catch (IOException | WriterException e) {
            throw new CouldNotProcessRequestException("QR code generation failed");
        }
        return bytes;
    }

    public ResponseDto<?> recordAttendance(String qrUUID) {
        AttendanceChecking checking = attendanceCheckingRepository.findById(UUID.fromString(qrUUID))
                .orElseThrow(() -> new ResourceNotFoundException("QR code is expired"));
        return recordAttendance(checking.getLessonId());
    }

    private ResponseDto<?> recordAttendance(Integer lessonId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Student student = studentRepository.findByUser_Username(username)
                .orElseThrow(() -> new UserNotFoundException("Student not found"));

        return recordAttendance(lessonId, student.getId());

    }

    private ResponseDto<?> recordAttendance(Integer lessonId, Integer studentId) {

        if (!lessonRepository.existsById(lessonId)) {
            throw new ResourceNotFoundException("Lesson with id %s not found".formatted(lessonId));
        }

        Attendance attendance = attendanceRepository.findAttendanceByStudentIdAndLessonId(studentId, lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));

        AttendanceStatus status = attendance.getStatus();
        if (status.equals(AttendanceStatus.ABSENT)) {
            attendance.setStatus(AttendanceStatus.PRESENT);
        } else if (status.equals(AttendanceStatus.PRESENT)) {
            throw new ResourceAlreadyExistsException("You have been recorded as %s"
                    .formatted(status.toString().toLowerCase()));
        }
        else {
            throw new ResourceAlreadyExistsException("You have been recorded as %s by teacher and cannot change it"
                    .formatted(status.toString().toLowerCase()));
        }

        attendanceRepository.save(attendance);

        return ResponseDto
                .builder()
                .code(200)
                .success(true)
                .message("Attendance has been recorded")
                .build();

    }
}
