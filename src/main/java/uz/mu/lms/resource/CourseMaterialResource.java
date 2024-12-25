package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.CourseMaterialDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.service.course.CourseMaterialService;

import java.util.List;

@RestController
@RequestMapping("/api/course-content")
@RequiredArgsConstructor
public class CourseMaterialResource {

    private final CourseMaterialService courseMaterialService;

    @GetMapping("/{courseId}")
    public ResponseEntity<ResponseDto<List<CourseMaterialDto>>> getAllCourses(
            @PathVariable Integer courseId) {
        return ResponseEntity.ok(courseMaterialService.getCourseContents(courseId));
    }

    @GetMapping("/{materialId}")
    public ResponseEntity<byte[]> getMaterialFile(
            @PathVariable Integer materialId) {
        return courseMaterialService.getMaterialFile(materialId);
    }

    @PostMapping
    public ResponseEntity<?> addCourseMaterial(
            @RequestParam Integer courseId,
            @RequestParam String title,
            @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(courseMaterialService.addCourseMaterial(courseId, title, file));
    }

    @PostMapping("/list")
    public ResponseDto<?> addMaterialToExistingList(
            @RequestParam Integer materialId,
            @RequestPart("file") MultipartFile file) {
        return courseMaterialService.addMaterialToExistingList(materialId, file);
    }
}
