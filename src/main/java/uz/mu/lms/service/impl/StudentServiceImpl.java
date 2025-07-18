package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.ScoreDto;
import uz.mu.lms.dto.StudentDto;
import uz.mu.lms.dto.StudentProfileDto;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.exceptions.FileNotSupportedException;
import uz.mu.lms.exceptions.UserAlreadyExistsException;
import uz.mu.lms.exceptions.UserNotFoundException;
import uz.mu.lms.model.Attachment;
import uz.mu.lms.model.Course;
import uz.mu.lms.model.GradingScale;
import uz.mu.lms.model.Student;
import uz.mu.lms.model.enums.RoleName;
import uz.mu.lms.projection.CourseGradeProjection;
import uz.mu.lms.repository.CourseRepository;
import uz.mu.lms.repository.RoleRepository;
import uz.mu.lms.repository.StudentRepository;
import uz.mu.lms.service.ContentService;
import uz.mu.lms.service.CourseService;
import uz.mu.lms.service.StudentService;
import uz.mu.lms.service.mapper.StudentMapper;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final StudentMapper studentMapper;
    private final ContentService contentService;
    private final PasswordEncoder passwordEncoder;
    private final CourseRepository courseRepository;

    @Override
    public StudentDto addStudent(StudentDto studentDto) {
        if (studentRepository.existsByUser_Username(studentDto.userDto().getUsername())) {
            throw new UserAlreadyExistsException("Student with username " + studentDto.userDto().getUsername() + " already exists");
        }

        if (studentRepository.existsByUser_PhoneNumber(studentDto.userDto().getPhoneNumber())) {
            throw new UserAlreadyExistsException("Student with phone number " + studentDto.userDto().getPhoneNumber() + " already exists");
        }

        if (studentRepository.existsByStudentId(studentDto.studentId())) {
            throw new UserAlreadyExistsException("Student with id " + studentDto.studentId() + " already exists");
        }

        Student student = studentMapper.toEntity(studentDto);
        student.getUser().setPassword(passwordEncoder.encode(studentDto.userDto().getPassword()));
        student.getUser().setRole(roleRepository.findByName(RoleName.ROLE_STUDENT.toString()));
        Student saved = studentRepository.save(student);

        return studentMapper.toDto(saved);
    }

    public void uploadProfileImage(MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        String contentType = file.getContentType();
        if (contentType == null || (!contentType.startsWith("image/"))) {
            throw new FileNotSupportedException(
                    "Only image files are allowed");
        }

        Student student = studentRepository.findByUser_Username(username)
                .orElseThrow(() -> new UserNotFoundException("Student with email " + username + " not found"));

        Integer attachmentId = contentService.createContent(file).getId();
        student.setPhotoAttachmentId(attachmentId);
        studentRepository.save(student);
    }

    public Attachment getProfileImage() {

        Student student = findCurrentStudent();

        if (student.getPhotoAttachmentId() == null) {
            throw new ResourceNotFoundException("User doesn't have a profile picture");
        }

        return contentService.retrieveContent(student.getPhotoAttachmentId());
    }

    public void deleteStudent(Integer id) {
        if (!studentRepository.existsById(id)) {
            throw new UserNotFoundException("Student with id " + id + " does not exist");
        }
        studentRepository.deleteById(id);
    }

    public List<StudentDto> getAllStudents(Pageable pageable) {
        Page<Student> students = studentRepository.findAll(pageable);

        return students.stream()
                .map(studentMapper::toDto).toList();
    }

    @Override
    public StudentProfileDto getProfileInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Student student = studentRepository.findByUser_Username(username)
                .orElseThrow(() -> new UserNotFoundException("Student with email " + username + " not found"));

        return studentMapper.toProfileDtoFromStudent(student);
    }

    @Override
    public StudentProfileDto updateProfileInfo(StudentProfileDto studentProfileDto) {

        Student student = findCurrentStudent();

        Student saved = studentRepository.save(studentMapper.toEntityFromProfileDto(studentProfileDto, student));

        return studentMapper.toProfileDtoFromStudent(saved);

    }


    @Override
    public List<Student> findStudentsByGroupIds(List<Integer> groupIds) {
        return studentRepository.findStudentsByGroupIds(groupIds);
    }

    @Override
    public Map<String, ScoreDto> getCourseGrades(Integer courseId) {
        CourseGradeProjection grade = studentRepository.findCourseGradeByStudentId(findCurrentStudent().getId(), courseId);

        if (grade == null) {
            throw new ResourceNotFoundException("Grades not found for course with id " + courseId);
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course with id " + courseId + " does not exist"));

        GradingScale gradingScale = course.getGradingScale();
        if (gradingScale == null) {
            throw new IllegalStateException("Grading scale is not set for course with id " + courseId);
        }

        Map<String, ScoreDto> result = new LinkedHashMap<>();

        double attendanceTotal = gradingScale.getAttendance() != null ? gradingScale.getAttendance() : 0.0;
        double attendanceEarned = (grade.getAttendancePresent() != null && grade.getAttendanceTotal() != null && grade.getAttendanceTotal() > 0)
                ? attendanceTotal * grade.getAttendancePresent() / grade.getAttendanceTotal()
                : 0.0;

        ScoreDto attendance = ScoreDto.builder()
                .earned((int) attendanceEarned)
                .total((int) attendanceTotal)
                .build();

        ScoreDto progress = ScoreDto.builder()
                .earned(grade.getProgress() != null ? grade.getProgress() : 0)
                .total(gradingScale.getProgress() != null ? gradingScale.getProgress() : 0)
                .build();

        ScoreDto midterm = ScoreDto.builder()
                .earned(grade.getMidterm() != null ? grade.getMidterm() : 0)
                .total(gradingScale.getMidterm() != null ? gradingScale.getMidterm() : 0)
                .build();

        ScoreDto finalExam = ScoreDto.builder()
                .earned(grade.getFinal() != null ? grade.getFinal() : 0)
                .total(gradingScale.getFinalExam() != null ? gradingScale.getFinalExam() : 0)
                .build();

        ScoreDto overall = ScoreDto.builder()
                .earned(attendance.earned() + progress.earned() + midterm.earned() + finalExam.earned())
                .total(attendance.total() + progress.total() + midterm.total() + finalExam.total())
                .build();

        result.put("attendance", attendance);
        result.put("progress", progress);
        result.put("midterm", midterm);
        result.put("finalExam", finalExam);
        result.put("overall", overall);

        return result;
    }



    @Override
    public Student findCurrentStudent() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return studentRepository.findByUser_Username(username)
                .orElseThrow(() -> new UserNotFoundException("Student with email " + username + " not found"));
    }
}
