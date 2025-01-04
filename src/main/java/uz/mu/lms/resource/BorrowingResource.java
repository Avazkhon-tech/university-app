package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.BorrowingDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.service.BorrowingService;

import java.util.List;

@RestController
@RequestMapping("/api/borrowings")
@RequiredArgsConstructor
public class BorrowingResource {

    private final BorrowingService borrowingService;

    @PostMapping("/borrow")
    public ResponseEntity<ResponseDto<?>> borrowBook(
            @RequestParam Integer bookId) {

        ResponseDto<?> response = borrowingService.borrowBook(bookId);
        return ResponseEntity.status(response.code()).body(response);
    }

    @PostMapping("/return")
    public ResponseEntity<ResponseDto<?>> returnBook(
            @RequestParam Integer borrowingId) {
        ResponseDto<?> response = borrowingService.returnBook(borrowingId);
        return ResponseEntity.status(response.code()).body(response);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<BorrowingDto>>> getAllBooks(
            @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(borrowingService.getBorrowedBooks(pageable));
    }
}
