package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public ResponseDto<List<BookCategoryDto>> getAllEBooksCategory() {
        return ResponseDto.success(eBookService.getAllBookCategories());
    }

    @GetMapping
    public PaginatedResponseDto<List<EBookDto>> getAllEBooks(@PageableDefault Pageable pageable, @RequestParam Integer categoryId) {
        List<EBookDto> eBooks = eBookService.getAllEBooks(pageable, categoryId);
        return PaginatedResponseDto.success(pageable.getPageNumber(), eBooks.size(), eBooks);
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
    public ResponseDto<?> deleteEBook(@PathVariable Integer bookId) {
        eBookService.deleteEBook(bookId);
        return ResponseDto.success("Book has been deleted");
    }
}
