package uz.mu.lms.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import uz.mu.lms.dto.CourseDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.projection.StudentCourseProjection;

import java.util.List;

public interface CourseService {

    PaginatedResponseDto<List<CourseDto>> getAllCourses(Pageable pageable);

    ResponseDto<CourseDto> createCourse(CourseDto courseDto);

//    ResponseDto<?> enrollStudent(Integer studentId, Integer courseId);

    List<StudentCourseProjection> getCoursesStudent(Authentication authentication);
 }
