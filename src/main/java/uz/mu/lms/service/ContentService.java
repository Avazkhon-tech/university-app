package uz.mu.lms.service;

import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.model.Attachment;

public interface ContentService {

    Attachment retrieveContent(Integer id);

    Attachment createContent(MultipartFile file);
}
