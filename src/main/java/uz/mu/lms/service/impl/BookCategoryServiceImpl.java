package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.BookCategoryDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.model.BookCategory;
import uz.mu.lms.repository.BookCategoryRepository;
import uz.mu.lms.service.BookCategoryService;
import uz.mu.lms.service.mapper.BookCategoryMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCategoryServiceImpl implements BookCategoryService {

    private final BookCategoryRepository bookCategoryRepository;
    private final BookCategoryMapper bookCategoryMapper;

    @Override
    public BookCategoryDto createBookCategory(BookCategoryDto bookCategoryDto) {

        BookCategory saved = bookCategoryRepository.save(bookCategoryMapper.toEntity(bookCategoryDto));
        return bookCategoryMapper.toDto(saved);
    }

    @Override
    public List<BookCategoryDto> getAllBookCategories() {
        return bookCategoryRepository.findAll()
                .stream()
                .map(bookCategoryMapper::toDto)
                .toList();
    }
}
