package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.model.Attachment;
import uz.mu.lms.service.ContentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attachment")
public class AttachmentResource {

    private final ContentService contentService;

    @GetMapping("/{attachmentId}")
    public ResponseEntity<byte[]> getAttachment(@PathVariable Integer attachmentId) {
        Attachment attachment = contentService.retrieveContent(attachmentId);

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        attachment.getFilename().substring(attachment.getFilename().indexOf('_') + 1) + "\"")
                .body(attachment.getBytes());
    }
}
