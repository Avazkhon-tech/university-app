package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.BookCategoryDto;
import uz.mu.lms.dto.BorrowingDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.exceptions.ResourceAlreadyExistsException;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.exceptions.UserNotFoundException;
import uz.mu.lms.model.BookCategory;
import uz.mu.lms.model.Borrowing;
import uz.mu.lms.model.PhysicalBook;
import uz.mu.lms.model.User;
import uz.mu.lms.repository.BookCategoryRepository;
import uz.mu.lms.repository.BorrowingRepository;
import uz.mu.lms.repository.PhysicalBookRepository;
import uz.mu.lms.repository.UserRepository;
import uz.mu.lms.service.BookCategoryService;
import uz.mu.lms.service.BorrowingService;
import uz.mu.lms.service.mapper.BookCategoryMapper;
import uz.mu.lms.service.mapper.BorrowingMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCategoryServiceImpl implements BookCategoryService {

    private final BookCategoryRepository bookCategoryRepository;
    private final BookCategoryMapper bookCategoryMapper;

    @Override
    public ResponseDto<BookCategoryDto> createBookCategory(BookCategoryDto bookCategoryDto) {

        BookCategory saved = bookCategoryRepository.save(bookCategoryMapper.toEntity(bookCategoryDto));
        return ResponseDto
                .<BookCategoryDto>builder()
                .message("Category created successfully")
                .code(200)
                .success(true)
                .data(bookCategoryMapper.toDto(saved))
                .build();
    }

    @Override
    public ResponseDto<List<BookCategoryDto>> getAllBookCategories() {
        List<BookCategoryDto> list = bookCategoryRepository.findAll()
                .stream()
                .map(bookCategoryMapper::toDto)
                .toList();

        return ResponseDto.<List<BookCategoryDto>>
                builder()
                .message("OK")
                .success(true)
                .code(200)
                .data(list)
                .build();
    }
}
