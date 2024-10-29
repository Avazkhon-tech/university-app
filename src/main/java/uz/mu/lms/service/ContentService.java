package uz.mu.lms.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uz.mu.lms.model.Content;
import uz.mu.lms.repository.ContentRepository;
import uz.mu.lms.repository.NewsRepository;

import java.io.IOException;

@Service
public class ContentService {

    private final ContentRepository contentRepository;
    private final NewsRepository newsRepository;

    public ContentService(ContentRepository contentRepository, NewsRepository newsRepository) {
        this.contentRepository = contentRepository;
        this.newsRepository = newsRepository;
    }

    public ResponseEntity<byte[]> getImageNews(Long id) throws IOException {
        return getFile(id);
    }

    public ResponseEntity<byte[]> getFile(Long id) throws IOException {
        Long id1 = Long.valueOf(newsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getContent().getId());

        Content byId = contentRepository.findById(id1).get();

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(byId.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        byId.getFileName().substring(byId.getFileName().indexOf('_') + 1) + "\"")
                .body(byId.getBytes());
    }
}

