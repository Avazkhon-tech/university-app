package uz.mu.lms.service.course;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import uz.mu.lms.dto.CourseDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;

import java.util.List;

public interface ICourseService {

    ResponseEntity<PaginatedResponseDto<List<CourseDto>>> getAllCourses(Pageable pageable);

    ResponseEntity<ResponseDto<CourseDto>> createCourse(CourseDto courseDto);
}
