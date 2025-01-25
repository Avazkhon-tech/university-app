package uz.mu.lms.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.NewsDto;
import uz.mu.lms.model.Attachment;

import java.util.List;

public interface NewsService {

    Attachment getNewsImage(Integer id);
    List<NewsDto> getNews(Pageable pageable);
    NewsDto createEvent(MultipartFile file, NewsDto newsDto);
}
