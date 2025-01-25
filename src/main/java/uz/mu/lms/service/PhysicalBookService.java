package uz.mu.lms.service;


import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.BookCategoryDto;
import uz.mu.lms.dto.PhysicalBookDto;

import java.util.List;

public interface PhysicalBookService {

    List<PhysicalBookDto> getAllPhysicalBooks(Pageable pageable, Integer categoryId);

    PhysicalBookDto addPhysicalBook(PhysicalBookDto bookDto, MultipartFile coverImage);

    void deletePhysicalBook(Integer bookId);

    List<BookCategoryDto> getPhysicalBookCategories();
}
