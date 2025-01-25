package uz.mu.lms.service;

import org.springframework.data.domain.Pageable;
import uz.mu.lms.dto.BorrowingDto;

import java.util.List;

public interface BorrowingService {

    void borrowBook(Integer bookId);

    void returnBook(Integer borrowingId);

    List<BorrowingDto> getBorrowedBooks(Pageable pageable);


}
