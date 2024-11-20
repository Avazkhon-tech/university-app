package uz.mu.lms.service.news;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.NewsDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.model.News;
import uz.mu.lms.repository.NewsRepository;
import uz.mu.lms.service.content.ContentService;
import uz.mu.lms.service.mapper.NewsMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final ContentService contentService;
    private final NewsMapper newsMapper;

    @Value("${host}")
    private String hostAddr;

    public ResponseEntity<byte[]> getNewsImage(Integer id) {
        return contentService.retrieveContent(id);
    }

    public PaginatedResponseDto<List<NewsDto>> getNews(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<News> newsPage = newsRepository.findAllByOrderByIdDesc(pageable);

        List<NewsDto> dtoList = newsPage
                .stream()
                .map(newsMapper::toDto)
                .toList();

        return PaginatedResponseDto
                .<List<NewsDto>>builder()
                .code(HttpStatus.OK.value())
                .success(true)
                .data(dtoList)
                .page(page)
                .size(size)
                .build();
    }

    public ResponseDto<NewsDto> createEvent(MultipartFile file, NewsDto newsDto) {

        Integer contentId = contentService.createContent(file);
        News news = newsMapper.toEntity(newsDto);
        news.setImageUrl(generateImageUrl(contentId));
        NewsDto dto = newsMapper.toDto(newsRepository.save(news));

        return ResponseDto.<NewsDto>builder()
                .data(dto)
                .code(HttpStatus.OK.value())
                .success(true)
                .message("News created")
                .build();
    }

    public String generateImageUrl(Integer id) {
        return hostAddr + "/api/image/" + id;
    }
}
