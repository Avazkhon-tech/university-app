package uz.mu.lms.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uz.mu.lms.model.Content;
import uz.mu.lms.repository.ContentRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ContentService {

    @Value("${dir_images}")
    private String dirImages;

    private final ContentRepository contentRepository;

    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    public ResponseEntity<byte[]> getImageNews(Long id) throws IOException {
        Content content = contentRepository.findById(id).orElse(null);
        if (content == null) {
            return ResponseEntity.notFound().build();
        }

        Path filePath = Paths.get(dirImages, content.getFileName());
        return getFile(filePath, content);
    }



    public ResponseEntity<byte[]> getFile(Path filePath, Content attachment) throws IOException {
        if (!Files.exists(filePath)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        }
        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        attachment.getFileName().substring(attachment.getFileName().indexOf('_') + 1) + "\"")
                .body(Files.readAllBytes(filePath));
    }
}

