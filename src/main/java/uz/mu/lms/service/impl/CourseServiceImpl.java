package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.CourseDto;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.model.Course;
import uz.mu.lms.model.Department;
import uz.mu.lms.model.Student;
import uz.mu.lms.projection.StudentCourseProjection;
import uz.mu.lms.repository.CourseRepository;
import uz.mu.lms.repository.DepartmentRepository;
import uz.mu.lms.repository.StudentRepository;
import uz.mu.lms.service.CourseService;
import uz.mu.lms.service.StudentService;
import uz.mu.lms.service.mapper.CourseMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor


public class CourseServiceImpl implements CourseService {

    @Value("${spring.defaultValues.host}")
    String hostAddr;

    private final StudentService studentService;
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseMapper courseMapper;

    @Override
    public List<CourseDto> getAllCourses(Pageable pageable) {
        return courseRepository
                .findAll(pageable)
                .stream()
                .map(courseMapper::toDto)
                .toList();
    }

    @Override
    public CourseDto createCourse(CourseDto courseDto) {

        Optional<Department> department = departmentRepository.findById(courseDto.departmentId());

        if (department.isEmpty()) {
            throw new ResourceNotFoundException("Department with id %d not found".formatted(courseDto.departmentId()));
        }

        Course course = courseMapper.toEntity(courseDto);

        course.setDepartment(department.get());
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toDto(savedCourse);

    }


    @Override
    public List<StudentCourseProjection> getCoursesStudent() {
        Student student = studentService.findCurrentStudent();
        return courseRepository.findByStudentId(student.getId(), hostAddr);
    }

    @Override
    public Course findCourseById(Integer id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Course with id %d not found".formatted(id))
        );
    }
}
