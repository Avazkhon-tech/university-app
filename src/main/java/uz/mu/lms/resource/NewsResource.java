package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.NewsDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.service.news.INewsService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NewsResource {

    private final INewsService newsService;

    // ONLY FOR ADMINS
    @PostMapping("/upload-event")
    public ResponseEntity<ResponseDto<NewsDto>> uploadEvent(@RequestPart("file") MultipartFile file, @RequestPart NewsDto newsDto) {
        return newsService.createEvent(file, newsDto);
    }

    // FOR ALL
    @GetMapping("/get-events")
    public ResponseEntity<PaginatedResponseDto<List<NewsDto>>> getNews(
            @PageableDefault(page = 0, size = 10) Pageable pageable)
             {
        return newsService.getNews(pageable);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        return newsService.getNewsImage(id);
    }
}

