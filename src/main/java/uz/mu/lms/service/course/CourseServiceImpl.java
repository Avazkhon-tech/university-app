package uz.mu.lms.service.course;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.CourseDto;
import uz.mu.lms.dto.MyUserDetails;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.exceptions.UserNotFoundException;
import uz.mu.lms.model.*;
import uz.mu.lms.projection.StudentCourseProjection;
import uz.mu.lms.repository.CourseRepository;
import uz.mu.lms.repository.DepartmentRepository;
import uz.mu.lms.repository.StudentRepository;
import uz.mu.lms.service.mapper.CourseMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor


public class CourseServiceImpl implements CourseService {

    @Value("${spring.defaultValues.host}")
    String hostAddr;

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;

    @Override
    public PaginatedResponseDto<List<CourseDto>> getAllCourses(Pageable pageable) {

        var courses = courseRepository
                .findAll(pageable)
                .stream()
                .map(courseMapper::toDto)
                .toList();

        return PaginatedResponseDto.<List<CourseDto>>builder()
                .page(pageable.getPageSize())
                .size(courses.size())
                .success(true)
                .code(200)
                .message("Successfully retrieved courses")
                .data(courses)
                .build();

    }

    @Override
    public ResponseDto<CourseDto> createCourse(CourseDto courseDto) {

        Optional<Department> department = departmentRepository.findById(courseDto.department().id());

        if (department.isEmpty()) {
            throw new ResourceNotFoundException("Department with id %d not found".formatted(courseDto.department().id()));
        }

        var course = courseMapper.toEntity(courseDto);

        course.setDepartment(department.get());
        Course savedCourse = courseRepository.save(course);
        CourseDto dtoCourse = courseMapper.toDto(savedCourse);

        return ResponseDto.<CourseDto>builder()
                .code(200)
                .message("Successfully created the course")
                .success(true)
                .data(dtoCourse)
                .build();

    }

    @Override
    public ResponseDto<?> enrollStudent(Integer studentId, Integer courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new UserNotFoundException("Student with id %d not found"
                        .formatted(studentId)));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course with id %d not found"));

        if (!course.getStudents().contains(student)) {
            course.getStudents().add(student);
        }

        courseRepository.save(course);
        return ResponseDto.builder()
                .code(200)
                .message("Successfully enrolled student in %s".formatted(course.getTitle()))
                .success(true)
                .build();
    }

    @Override
    public ResponseDto<List<CourseDto>> getCoursesStudent(Authentication authentication) {
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        Student student = studentRepository.findByUser_Username(principal.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Student with username %s not found"
                .formatted(principal.getUsername())));

        List<CourseDto> list = student
                .getCourses()
                .stream()
                .map(courseMapper::toDto)
                .toList();

        return ResponseDto.<List<CourseDto>>builder()
                .code(200)
                .message("Successfully retrieved courses")
                .success(true)
                .data(list)
                .build();
    }

    @Override
    public List<StudentCourseProjection> getCoursesStudent2(Authentication authentication) {
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        Student student = studentRepository.findByUser_Username(principal.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Student with username %s not found"
                .formatted(principal.getUsername())));
        return courseRepository.findByStudentId(student.getId(), hostAddr);


    }
}
