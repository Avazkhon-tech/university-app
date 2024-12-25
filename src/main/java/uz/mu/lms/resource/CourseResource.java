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
import uz.mu.lms.service.course.CourseService;

import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseResource {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<List<CourseDto>>> getCourses(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(courseService.getAllCourses(pageable));
    }

    @GetMapping("/student")
    public ResponseEntity<List<StudentCourseProjection>> getCourses(Authentication authentication) {
        return ResponseEntity.ok(courseService.getCoursesStudent(authentication));
    }

    @PostMapping
    public ResponseEntity<ResponseDto<CourseDto>> createDepartment(@RequestBody CourseDto courseDto) {
        return ResponseEntity.ok(courseService.createCourse(courseDto));
    }

    @PostMapping("/enroll-student")
    public ResponseEntity<ResponseDto<?>> enrollStudent(
            @RequestParam Integer studentId,
            @RequestParam Integer courseId) {
        return ResponseEntity.ok(courseService.enrollStudent(studentId, courseId));
    }


}
