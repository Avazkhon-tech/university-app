package uz.mu.lms.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.NewsDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.exceptions.FileStorageException;
import uz.mu.lms.model.Content;
import uz.mu.lms.model.News;
import uz.mu.lms.repository.ContentRepository;
import uz.mu.lms.repository.NewsRepository;
import uz.mu.lms.service.mapper.NewsMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final ContentRepository attachmentRepository;
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper = Mappers.getMapper(NewsMapper.class);

    @Value("${dir_images}")
    private String uploadFolder;
    @Value("${host}")
    private String hostAddr;

    public ResponseDto<NewsDto> createEvent(MultipartFile file, NewsDto newsDto) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File is empty");
        }

        String extension = getFileExtension(file.getOriginalFilename());
        String name = UUID.randomUUID() + "." + extension;

        Content attachment = Content
                .builder()
                .originalName(file.getOriginalFilename())
                .fileName(name)
                .size(file.getSize())
                .fileType(file.getContentType())
                .build();


        attachmentRepository.save(attachment);

        try {
            Path path = Paths.get(uploadFolder +  name);
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new FileStorageException("File could not be uploaded");
        }

        News news = newsMapper.toEntity(newsDto);
        News newsSaved = newsRepository.save(news);

        NewsDto dto = newsMapper.toDto(newsSaved);
        dto.setContentUrl(generateImageUrl(newsSaved.getId()));

        return ResponseDto.<NewsDto>builder()
                .data(dto)
                .code(HttpStatus.OK.value())
                .success(true)
                .message("News created")
                .build();
    };

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

    public String generateImageUrl(Long id) {
        return hostAddr + "/api/image/" + id;
    }

    private String getFileExtension(String filename) throws BadRequestException {
        if (filename == null || filename.isBlank()) {
            throw new BadRequestException("Filename is invalid");
        }
        String[] split = filename.split("\\.");
        return split.length > 1 ? split[split.length - 1] : "";
    }
}
