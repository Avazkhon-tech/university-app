package uz.mu.lms.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.NewsDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;

import java.util.List;

public interface NewsService {

    ResponseEntity<byte[]> getNewsImage(Integer id);
    ResponseEntity<PaginatedResponseDto<List<NewsDto>>> getNews(Pageable pageable);
    ResponseEntity<ResponseDto<NewsDto>> createEvent(MultipartFile file, NewsDto newsDto);
}
