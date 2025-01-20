package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.LectureTemplateDto;
import uz.mu.lms.dto.LessonTemplateDto;
import uz.mu.lms.service.LessonTemplateService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lesson-template")
public class LessonTemplateResource {

    private final LessonTemplateService lessonTemplateService;

    @GetMapping
    public ResponseEntity<String> getAllTemplates() {
        return ResponseEntity.ok().body("Empty");
    }

    @PostMapping("/single-group")
    public ResponseEntity<String> createTemplate(@RequestBody LessonTemplateDto lessonTemplateDto,
                                                 @RequestParam Integer groupId) {
        lessonTemplateService.createTemplate(lessonTemplateDto, groupId);
        return ResponseEntity.ok().body("Successfully created the template");
    }

    @PostMapping("/multi-group")
    public ResponseEntity<String> createTemplate(@RequestBody LectureTemplateDto lectureTemplateDto) {
        lessonTemplateService.createTemplate(lectureTemplateDto);
        return ResponseEntity.ok().body("Successfully created the template");
    }
}
