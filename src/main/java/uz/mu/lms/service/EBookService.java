package uz.mu.lms.service;


import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.BookCategoryDto;
import uz.mu.lms.dto.EBookDto;

import java.util.List;

public interface EBookService {

    List<BookCategoryDto> getAllBookCategories();

    List<EBookDto> getAllEBooks(Pageable pageable, Integer categoryId);

    EBookDto addEBook(EBookDto eBookDTO, MultipartFile coverImage, MultipartFile bookFile);

    void deleteEBook(Integer bookId);
}
