package uz.mu.lms.resource;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.CourseGroupDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.service.ContentService;
import uz.mu.lms.service.CourseGroupService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attachment")
public class AttachmentResource {

    private final ContentService contentService;

    @GetMapping("/{attachmentId}")
    public ResponseEntity<byte[]> getAttachment(@PathVariable Integer attachmentId) {
        return contentService.retrieveContent(attachmentId);
    }
}
