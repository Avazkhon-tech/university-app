package uz.mu.lms.service.content;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.exceptions.ContentDoesNotExistException;
import uz.mu.lms.exceptions.FileNotSupportedException;
import uz.mu.lms.model.Content;
import uz.mu.lms.repository.ContentRepository;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContentService implements IContentService {

    private final ContentRepository contentRepository;

    public ResponseEntity<byte[]> retrieveContent(Long id) {
        Content byId = contentRepository.findById(id)
                .orElseThrow(() -> new ContentDoesNotExistException("Content with id " + id + " not found"));

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(byId.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        byId.getFileName().substring(byId.getFileName().indexOf('_') + 1) + "\"")
                .body(byId.getBytes());
    }

    public Long createContent(MultipartFile file) {

        if (file == null || file.isEmpty() || file.getOriginalFilename() != null)
            throw new FileNotSupportedException("File can not be empty");

        String extension = getContentExtension(file.getOriginalFilename());
        String name = UUID.randomUUID() + "." + extension;

        try {
            Content content = Content
                    .builder()
                    .originalName(file.getOriginalFilename())
                    .fileName(name)
                    .size(file.getSize())
                    .bytes(file.getBytes())
                    .fileType(file.getContentType())
                    .build();
            return contentRepository.save(content).getId();

        } catch (IOException e) {
            throw new FileNotSupportedException("File not supported");
        }

    }

    private String getContentExtension(String filename)  {
        String[] split = filename.split("\\.");
        return split.length > 1 ? split[split.length - 1] : "";
    }
}

