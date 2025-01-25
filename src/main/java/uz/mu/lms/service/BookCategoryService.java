package uz.mu.lms.service;

import uz.mu.lms.dto.BookCategoryDto;

import java.util.List;

public interface BookCategoryService {

    BookCategoryDto createBookCategory(BookCategoryDto bookCategoryDto);

    List<BookCategoryDto> getAllBookCategories();
}
