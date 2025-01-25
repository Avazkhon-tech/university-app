package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.NewsDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.model.Attachment;
import uz.mu.lms.service.NewsService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NewsResource {

    private final NewsService newsService;

    // ONLY FOR ADMINS
    @PostMapping("/upload-event")
    public ResponseDto<NewsDto> uploadEvent(@RequestPart("file") MultipartFile file, @RequestPart NewsDto newsDto) {
        NewsDto event = newsService.createEvent(file, newsDto);
        return ResponseDto.success(event);
    }

    // FOR ALL
    @GetMapping("/get-events")
    public PaginatedResponseDto<List<NewsDto>> getNews(@PageableDefault Pageable pageable) {
        List<NewsDto> news = newsService.getNews(pageable);
        return PaginatedResponseDto.success(pageable.getPageNumber(), news.size(), news);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        Attachment attachment = newsService.getNewsImage(id);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        attachment.getFilename().substring(attachment.getFilename().indexOf('_') + 1) + "\"")
                .body(attachment.getBytes());
    }
}

