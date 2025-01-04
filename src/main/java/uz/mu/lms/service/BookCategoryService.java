package uz.mu.lms.service;

import uz.mu.lms.dto.BookCategoryDto;
import uz.mu.lms.dto.ResponseDto;

import java.util.List;

public interface BookCategoryService {

    ResponseDto<BookCategoryDto> createBookCategory(BookCategoryDto bookCategoryDto);

    ResponseDto<List<BookCategoryDto>> getAllBookCategories();
}
