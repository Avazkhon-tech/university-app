package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.BookCategoryDto;
import uz.mu.lms.dto.BorrowingDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.service.BookCategoryService;
import uz.mu.lms.service.BorrowingService;

import java.util.List;

@RestController
@RequestMapping("/api/book-category")
@RequiredArgsConstructor
public class BookCategoryResource {

    private final BookCategoryService bookCategoryService;

    @GetMapping
    public ResponseDto<List<BookCategoryDto>> getAllCategories() {
        List<BookCategoryDto> allBookCategories = bookCategoryService.getAllBookCategories();
        return ResponseDto.success(allBookCategories);
    }

    @PostMapping
    public ResponseDto<BookCategoryDto> addCategory(@RequestBody BookCategoryDto bookCategoryDto) {
        BookCategoryDto bookCategory = bookCategoryService.createBookCategory(bookCategoryDto);
        return ResponseDto.success(bookCategory, "Book category has been created successfully");
    }
}
