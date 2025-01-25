package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.CourseDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.projection.StudentCourseProjection;
import uz.mu.lms.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseResource {

    private final CourseService courseService;

    @GetMapping
    public PaginatedResponseDto<List<CourseDto>> getCourses(@PageableDefault Pageable pageable) {
        List<CourseDto> courses = courseService.getAllCourses(pageable);
        return PaginatedResponseDto.success(pageable.getPageSize(), courses.size(), courses);
    }

    @GetMapping("/student")
    public ResponseDto<List<StudentCourseProjection>> getCourses() {
        return ResponseDto.success(courseService.getCoursesStudent());
    }

    @PostMapping
    public ResponseDto<CourseDto> createCourse(@RequestBody CourseDto courseDto) {
        return ResponseDto.success(courseService.createCourse(courseDto));
    }

}
