package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.CourseDto;
import uz.mu.lms.dto.UserDetailsDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.exceptions.UserNotFoundException;
import uz.mu.lms.model.*;
import uz.mu.lms.projection.StudentCourseProjection;
import uz.mu.lms.repository.CourseRepository;
import uz.mu.lms.repository.DepartmentRepository;
import uz.mu.lms.repository.StudentRepository;
import uz.mu.lms.service.CourseService;
import uz.mu.lms.service.mapper.CourseMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor


public class CourseServiceImpl implements CourseService {

    @Value("${spring.defaultValues.host}")
    String hostAddr;

    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;
    private final CourseMapper courseMapper;

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

        Optional<Department> department = departmentRepository.findById(courseDto.departmentId());

        if (department.isEmpty()) {
            throw new ResourceNotFoundException("Department with id %d not found".formatted(courseDto.departmentId()));
        }

        Course course = courseMapper.toEntity(courseDto);

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
    public List<StudentCourseProjection> getCoursesStudent(Authentication authentication) {
        UserDetailsDto principal = (UserDetailsDto) authentication.getPrincipal();
        Student student = studentRepository.findByUser_Username(principal.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Student with username %s not found"
                .formatted(principal.getUsername())));
        return courseRepository.findByStudentId(student.getId(), hostAddr);

    }

    @Override
    public Course findCourseById(Integer id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Course with id %d not found".formatted(id))
        );
    }
}
