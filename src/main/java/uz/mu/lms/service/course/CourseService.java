package uz.mu.lms.service.course;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.CourseDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.TeacherDto;
import uz.mu.lms.exceptions.ContentDoesNotExistException;
import uz.mu.lms.model.Course;
import uz.mu.lms.model.Department;
import uz.mu.lms.model.Teacher;
import uz.mu.lms.repository.CourseRepository;
import uz.mu.lms.repository.DepartmentRepository;
import uz.mu.lms.repository.TeacherRepository;
import uz.mu.lms.service.mapper.CourseMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final DepartmentRepository departmentRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public ResponseEntity<PaginatedResponseDto<List<CourseDto>>> getAllCourses(Pageable pageable) {

        var courses = courseRepository
                .findAll(pageable)
                .stream()
                .map(courseMapper::toDto)
                .toList();

        var response = PaginatedResponseDto.<List<CourseDto>>builder()
                .page(pageable.getPageSize())
                .size(courses.size())
                .success(true)
                .code(200)
                .message("Successfully retrieved courses")
                .data(courses)
                .build();

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ResponseDto<CourseDto>> createCourse(CourseDto courseDto) {

        Optional<Department> department = departmentRepository.findById(courseDto.department().id());

        if (department.isEmpty()) {
            throw new ContentDoesNotExistException("Department with id %d not found".formatted(courseDto.department().id()));
        }

        var course = courseMapper.toEntity(courseDto);

        course.setDepartment(department.get());
        Course savedCourse = courseRepository.save(course);
        CourseDto dtoCourse = courseMapper.toDto(savedCourse);

        var response = ResponseDto.<CourseDto>builder()
                .code(200)
                .message("Successfully created the course")
                .success(true)
                .data(dtoCourse)
                .build();

        return ResponseEntity.ok(response);
    }
}
