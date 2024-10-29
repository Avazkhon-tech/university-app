package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.NewsDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.service.NewsService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NewsResource {

    private final NewsService newsService;

    @PostMapping("/upload-event")
    public ResponseDto<NewsDto> uploadEvent(@RequestPart("file") MultipartFile file, @RequestPart NewsDto newsDto) throws IOException {
        return newsService.createEvent(file, newsDto);
    }

    @GetMapping("/get-events/{page}/{size}")
    public PaginatedResponseDto<List<NewsDto>> getNews(@PathVariable(value = "page") Integer page, @PathVariable(value = "size") Integer size) {
        return newsService.getNews(page, size);
    }
}

