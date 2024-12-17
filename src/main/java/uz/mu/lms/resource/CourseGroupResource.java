package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.CourseGroupDto;
import uz.mu.lms.projection.CourseGroupProjection;
import uz.mu.lms.service.course.CourseGroupService;
import uz.mu.lms.service.course.CourseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class CourseGroupResource {

    private final CourseGroupService courseGroupService;

    @GetMapping
    public List<CourseGroupProjection> getAllCourses(@RequestParam Integer courseId ) {
        return courseGroupService.getAllGroups(courseId);
    }

    @PostMapping
    public boolean createGroup(@RequestParam Integer courseId,
                               @RequestBody CourseGroupDto courseGroupDto) {
        courseGroupService.createGroup(courseGroupDto, courseId);
        return true;
    }
}
