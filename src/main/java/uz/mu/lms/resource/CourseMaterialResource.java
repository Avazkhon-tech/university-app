package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.CourseMaterialDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.service.CourseMaterialService;

import java.util.List;

@RestController
@RequestMapping("/api/course-content")
@RequiredArgsConstructor
public class CourseMaterialResource {

    private final CourseMaterialService courseMaterialService;

    @GetMapping("/files/{courseId}")
    public ResponseDto<List<CourseMaterialDto>> getAllCourses(@PathVariable Integer courseId) {
        return ResponseDto.success(courseMaterialService.getCourseContents(courseId));
    }

    @PostMapping
    public ResponseDto<?> addCourseMaterial(
            @RequestParam Integer courseId,
            @RequestParam String title,
            @RequestPart("file") MultipartFile file) {
        courseMaterialService.addCourseMaterial(courseId, title, file);
        return ResponseDto.success("OK");
    }

    @PostMapping("/list")
    public ResponseDto<?> addMaterialToExistingList(
            @RequestParam Integer materialId,
            @RequestPart("file") MultipartFile file) {
        courseMaterialService.addMaterialToExistingList(materialId, file);
        return ResponseDto.success("OK");
    }
}
