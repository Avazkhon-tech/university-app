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
    public ResponseDto<?> borrowBook(@RequestParam Integer bookId) {
        borrowingService.borrowBook(bookId);
        return ResponseDto.success("You can collect the book from the library using your student ID.");
    }

    @PostMapping("/return")
    public ResponseDto<?> returnBook(@RequestParam Integer borrowingId) {
        borrowingService.returnBook(borrowingId);
        return ResponseDto.success("Returned the book successfully.");
    }

    @GetMapping
    public ResponseDto<List<BorrowingDto>> getAllBooks(@PageableDefault Pageable pageable) {
        return ResponseDto.success(borrowingService.getBorrowedBooks(pageable), "List of borrowings that have not been returned yet");
    }
}
