package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.CourseGroupDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.projection.StudentCourseProjection;
import uz.mu.lms.service.course.CourseGroupService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class CourseGroupResource {

    private final CourseGroupService courseGroupService;

    @GetMapping
    public List<StudentCourseProjection> getAllCourses(
            @RequestParam Integer courseId ) {
        return courseGroupService.getAllGroups(courseId);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<CourseGroupDto>> createGroup(@RequestParam Integer courseId,
                                                                   @RequestBody CourseGroupDto courseGroupDto) {
        return ResponseEntity.ok(courseGroupService.createGroup(courseGroupDto, courseId));
    }

    @PostMapping("/student")
    public ResponseEntity<ResponseDto<?>> enrollStudentInGroup(
            @RequestParam Integer groupId,
            @RequestParam Integer studentId
    ) {
        return ResponseEntity.ok(courseGroupService.enrollStudentInGroup(groupId, studentId));
    }
}
