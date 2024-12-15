package uz.mu.lms.service.content;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.model.Attachment;

public interface IContentService {

    ResponseEntity<byte[]> retrieveContent(Integer id);

    Attachment createContent(MultipartFile file);
}
