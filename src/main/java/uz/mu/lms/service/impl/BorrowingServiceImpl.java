package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.BorrowingDto;
import uz.mu.lms.exceptions.ResourceAlreadyExistsException;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.exceptions.UserNotFoundException;
import uz.mu.lms.model.Borrowing;
import uz.mu.lms.model.PhysicalBook;
import uz.mu.lms.model.User;
import uz.mu.lms.repository.BorrowingRepository;
import uz.mu.lms.repository.PhysicalBookRepository;
import uz.mu.lms.repository.UserRepository;
import uz.mu.lms.service.BorrowingService;
import uz.mu.lms.service.mapper.BorrowingMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowingServiceImpl implements BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final PhysicalBookRepository physicalBookRepository;
    private final UserRepository userRepository;
    private final BorrowingMapper borrowingMapper;

    @Override
    public void borrowBook(Integer bookId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        if (borrowingRepository.countBorrowingByUserIdAndReturnedAtIsNull(user.getId()) >= 2) {
            throw new ResourceAlreadyExistsException("You can only borrow two books at the same time");
        }

        if (borrowingRepository.existsByUserIdAndBook_IdAndReturnedAtIsNull(user.getId(), bookId)) {
            throw new ResourceAlreadyExistsException("You have already borrowed this book");
        }

        PhysicalBook book = physicalBookRepository.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        if (book.getQuantity() <= 0) {
            throw new ResourceNotFoundException("Book is out of stock");
        }

        book.setQuantity(book.getQuantity() - 1);

        Borrowing borrowing = Borrowing.builder()
            .book(book)
            .user(user)
            .borrowedAt(LocalDateTime.now())
            .dueDate(LocalDateTime.now().plusWeeks(2))
            .build();

        borrowingRepository.save(borrowing);

    }

    @Override
    public void returnBook(Integer borrowingId) {
        Borrowing borrowing = borrowingRepository.findByIdAndReturnedAtIsNull(borrowingId)
            .orElseThrow(() -> new ResourceNotFoundException("Borrowing record not found"));

        borrowing.setReturnedAt(LocalDateTime.now());
        borrowing.getBook().setQuantity(borrowing.getBook().getQuantity() + 1);
        borrowingRepository.save(borrowing);
    }

    @Override
    public List<BorrowingDto> getBorrowedBooks(Pageable pageable) {
        return borrowingRepository.findByReturnedAtIsNull(pageable)
                .stream()
                .map(borrowingMapper::toDto)
                .toList();


    }
}
