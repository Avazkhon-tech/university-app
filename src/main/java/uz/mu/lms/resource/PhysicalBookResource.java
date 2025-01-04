package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.BookCategoryDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.PhysicalBookDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.service.PhysicalBookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/physical-book")
public class PhysicalBookResource {

    private final PhysicalBookService physicalBookService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<List<PhysicalBookDto>>> getAllEBooks(
            @PageableDefault Pageable pageable,
            @RequestParam Integer categoryId
    ) {

        List<PhysicalBookDto> allPhysicalBooks = physicalBookService.getAllPhysicalBooks(pageable, categoryId);

        PaginatedResponseDto<List<PhysicalBookDto>> response = PaginatedResponseDto.<List<PhysicalBookDto>>
                        builder()
                .data(allPhysicalBooks)
                .size(allPhysicalBooks.size())
                .page(pageable.getPageNumber())
                .message("OK")
                .success(true)
                .code(200)
                .build();

        return ResponseEntity.ok(response);
    }


    @GetMapping("/categories")
    public ResponseEntity<ResponseDto<List<BookCategoryDto>>> getAllCategories() {
        ResponseDto<List<BookCategoryDto>> list = physicalBookService.getPhysicalBookCategories();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<PhysicalBookDto> createPhysicalBook(
            @RequestPart("physicalBook") PhysicalBookDto physicalBookDto,
            @RequestPart("bookCoverImage") MultipartFile bookCoverImage) {
        PhysicalBookDto savedBook = physicalBookService.addPhysicalBook(physicalBookDto, bookCoverImage);
        return ResponseEntity.ok(savedBook);
    }


    // for admins
    @DeleteMapping("/{bookId}")
    public ResponseEntity<ResponseDto<?>> deleteEBook(@PathVariable Integer bookId) {
        ResponseDto<?> responseDto = physicalBookService.deletePhysicalBook(bookId);
        return ResponseEntity.ok(responseDto);
    }
}
