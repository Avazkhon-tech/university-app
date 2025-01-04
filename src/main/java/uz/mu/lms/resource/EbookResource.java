package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.BookCategoryDto;
import uz.mu.lms.dto.EBookDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.service.EBookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/e-book")
public class EbookResource {

    private final EBookService eBookService;

    @GetMapping("/categories")
    public ResponseEntity<ResponseDto<List<BookCategoryDto>>> getAllEBooksCategory() {
        return ResponseEntity.ok(eBookService.getAllBookCategories());
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<List<EBookDto>>> getAllEBooks(
            @PageableDefault Pageable pageable,
            @RequestParam Integer categoryId
    ) {

        List<EBookDto> allEBooks = eBookService.getAllEBooks(pageable, categoryId);

        PaginatedResponseDto<List<EBookDto>> response = PaginatedResponseDto.<List<EBookDto>>
                        builder()
                .data(allEBooks)
                .size(allEBooks.size())
                .page(pageable.getPageNumber())
                .message("OK")
                .success(true)
                .code(200)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<EBookDto> createEBook(
            @RequestPart("ebook") EBookDto eBookDTO,
            @RequestPart("bookCoverImage") MultipartFile bookCoverImage,
            @RequestPart("bookFile") MultipartFile bookFile) {
        EBookDto saved = eBookService.addEBook(eBookDTO, bookCoverImage, bookFile);
        return ResponseEntity.ok(saved);
    }

    // for admins
    @DeleteMapping("/{bookId}")
    public ResponseEntity<ResponseDto<?>> deleteEBook(@PathVariable Integer bookId) {
        ResponseDto<?> responseDto = eBookService.deleteEBook(bookId);
        return ResponseEntity.ok(responseDto);
    }
}
