package uz.mu.lms.service.content;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IContentService {

    ResponseEntity<byte[]> retrieveContent(Integer id);

    Integer createContent(MultipartFile file);
}
