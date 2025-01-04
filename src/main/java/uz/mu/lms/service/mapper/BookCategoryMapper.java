package uz.mu.lms.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mu.lms.dto.BookCategoryDto;
import uz.mu.lms.dto.BorrowingDto;
import uz.mu.lms.model.BookCategory;
import uz.mu.lms.model.Borrowing;

@Mapper(componentModel = "spring")
public abstract class BookCategoryMapper implements AbstractMapper<BookCategory, BookCategoryDto> {
}


