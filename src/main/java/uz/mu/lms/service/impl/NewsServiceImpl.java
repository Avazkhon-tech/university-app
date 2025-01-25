package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.NewsDto;
import uz.mu.lms.model.Attachment;
import uz.mu.lms.model.News;
import uz.mu.lms.repository.NewsRepository;
import uz.mu.lms.service.ContentService;
import uz.mu.lms.service.NewsService;
import uz.mu.lms.service.mapper.NewsMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final ContentService contentService;
    private final NewsMapper newsMapper;

    @Value("${spring.defaultValues.host}")
    private String hostAddr;

    public Attachment getNewsImage(Integer id) {
        return contentService.retrieveContent(id);
    }

    public List<NewsDto> getNews(Pageable pageable) {

        Page<News> newsPage = newsRepository.findAllByOrderByIdDesc(pageable);

        return newsPage
                .stream()
                .map(newsMapper::toDto)
                .toList();
    }

    public NewsDto createEvent(MultipartFile file, NewsDto newsDto) {

        Integer contentId = contentService.createContent(file).getId();
        News news = newsMapper.toEntity(newsDto);
        news.setImageUrl(hostAddr + "/api/image/" + contentId);
        return newsMapper.toDto(newsRepository.save(news));

    }
}
