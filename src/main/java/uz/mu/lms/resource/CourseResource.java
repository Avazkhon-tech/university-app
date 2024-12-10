package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.CourseDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.service.course.ICourseService;

import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseResource {

    private final ICourseService courseService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<List<CourseDto>>> getCourses(@PageableDefault Pageable pageable) {
        return courseService.getAllCourses(pageable);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<CourseDto>> createDepartment(@RequestBody CourseDto courseDto) {
        return courseService.createCourse(courseDto);
    }
}
