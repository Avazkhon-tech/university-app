package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.service.ContentService;

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
