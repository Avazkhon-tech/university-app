package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.exceptions.FileNotSupportedException;
import uz.mu.lms.model.Attachment;
import uz.mu.lms.repository.ContentRepository;
import uz.mu.lms.service.ContentService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;

    public Attachment retrieveContent(Integer id) {
        return contentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Content with id " + id + " not found"));
    }


    public Attachment createContent(MultipartFile file) {
        if (file == null || file.isEmpty() || file.getOriginalFilename() == null)
            throw new FileNotSupportedException("File can not be empty");

        try {
            Attachment attachment = Attachment
                    .builder()
                    .filename(file.getOriginalFilename())
                    .size(file.getSize())
                    .bytes(file.getBytes())
                    .fileType(file.getContentType())
                    .build();
            return contentRepository.save(attachment);

        } catch (IOException e) {
            throw new FileNotSupportedException("File not supported");
        }

    }
}

