package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.NewsDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.service.news.NewsService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NewsResource {

    private final NewsService newsService;

    @PostMapping("/upload-event")
    public ResponseDto<NewsDto> uploadEvent(@RequestPart("file") MultipartFile file, @RequestPart NewsDto newsDto) {
        return newsService.createEvent(file, newsDto);
    }

    @GetMapping("/get-events")
    public PaginatedResponseDto<List<NewsDto>> getNews(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        return newsService.getNews(page, size);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        return newsService.getNewsImage(id);
    }
}

