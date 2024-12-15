package uz.mu.lms.service.course;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.mu.lms.dto.CourseDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;

import java.util.List;

public interface CourseService {

    PaginatedResponseDto<List<CourseDto>> getAllCourses(Pageable pageable);

    ResponseDto<CourseDto> createCourse(CourseDto courseDto);

    ResponseDto<?> enrollStudent(Integer studentId, Integer courseId);

    ResponseDto<List<CourseDto>> getCoursesStudent(Authentication authentication);
 }
