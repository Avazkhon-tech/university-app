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
import uz.mu.lms.exceptions.ImageDoesNotExistException;
import uz.mu.lms.model.News;
import uz.mu.lms.repository.NewsRepository;
import uz.mu.lms.service.content.ContentService;
import uz.mu.lms.service.mapper.NewsMapper;

import java.io.IOException;
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
        Integer contentId = newsRepository.findById(id)
                .orElseThrow(() -> new ImageDoesNotExistException("Image with id " + id + " does not exists")).getAttachmentId();
        return contentService.retrieveContent(contentId);
    }

    public PaginatedResponseDto<List<NewsDto>> getNews(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<News> newsPage = newsRepository.findAllByOrderByIdDesc(pageable);

        List<NewsDto> dtoList = newsPage
                .stream()
                .map(newsMapper::toDto)
                .peek(newsDto -> newsDto.setContentUrl(generateImageUrl(newsDto.getId())))
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

    public ResponseDto<NewsDto> createEvent(MultipartFile file, NewsDto newsDto) throws IOException {

        Integer contentId = contentService.createContent(file);

        News news = newsMapper.toEntity(newsDto);

        news.setAttachmentId(contentId);
        News savedNews = newsRepository.save(news);
        NewsDto dto = newsMapper.toDto(savedNews);
        dto.setContentUrl(generateImageUrl(savedNews.getId()));

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
