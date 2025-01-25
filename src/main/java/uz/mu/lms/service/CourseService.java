package uz.mu.lms.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import uz.mu.lms.dto.CourseDto;
import uz.mu.lms.model.Course;
import uz.mu.lms.projection.StudentCourseProjection;

import java.util.List;

public interface CourseService {

    List<CourseDto> getAllCourses(Pageable pageable);

    CourseDto createCourse(CourseDto courseDto);

    List<StudentCourseProjection> getCoursesStudent();

    Course findCourseById(Integer id);
 }
