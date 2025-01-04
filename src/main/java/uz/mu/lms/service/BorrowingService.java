package uz.mu.lms.service;

import org.springframework.data.domain.Pageable;
import uz.mu.lms.dto.BorrowingDto;
import uz.mu.lms.dto.ResponseDto;

import java.util.List;

public interface BorrowingService {
    ResponseDto<?> borrowBook(Integer bookId);
    ResponseDto<?> returnBook(Integer borrowingId);
    ResponseDto<List<BorrowingDto>> getBorrowedBooks(Pageable pageable);

}
