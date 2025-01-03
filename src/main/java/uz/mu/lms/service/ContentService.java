package uz.mu.lms.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.model.Attachment;

public interface ContentService {

    ResponseEntity<byte[]> retrieveContent(Integer id);

    Attachment createContent(MultipartFile file);
}
