package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.BookCategoryDto;
import uz.mu.lms.dto.PhysicalBookDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.model.Attachment;
import uz.mu.lms.model.BookCategory;
import uz.mu.lms.model.EBook;
import uz.mu.lms.model.PhysicalBook;
import uz.mu.lms.repository.BookCategoryRepository;
import uz.mu.lms.repository.PhysicalBookRepository;
import uz.mu.lms.service.ContentService;
import uz.mu.lms.service.PhysicalBookService;
import uz.mu.lms.service.mapper.BookCategoryMapper;
import uz.mu.lms.service.mapper.PhysicalBookMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhysicalBookServiceImpl implements PhysicalBookService {

    private final PhysicalBookRepository physicalBookRepository;
    private final ContentService contentService;
    private final PhysicalBookMapper physicalBookMapper;
    private final BookCategoryMapper bookCategoryMapper;
    private final BookCategoryRepository bookCategoryRepository;


    @Override
    public List<PhysicalBookDto> getAllPhysicalBooks(Pageable pageable, Integer categoryId) {
        return physicalBookRepository.findAllByBookCategoryId(categoryId, pageable)
                .stream()
                .map(physicalBookMapper::toDto)
                .toList();
    }

    @Override
    public PhysicalBookDto addPhysicalBook(PhysicalBookDto bookDto, MultipartFile coverImage) {
        PhysicalBook physicalBook = physicalBookMapper.toEntity(bookDto);

        Optional<BookCategory> bookCategory = bookCategoryRepository.findById(bookDto.bookCategoryId());

         if (bookCategory.isEmpty()) {
            throw new ResourceNotFoundException("Book category not found");
        }

        Attachment coverImageAttachment = contentService.createContent(coverImage);
        physicalBook.setBookCategory(bookCategory.get());
        physicalBook.setBookCoverImage(coverImageAttachment);

        PhysicalBook bookSaved = physicalBookRepository.save(physicalBook);

        return physicalBookMapper.toDto(bookSaved);
    }

    @Override
    public ResponseDto<?> deletePhysicalBook(Integer bookId) {
         if(!physicalBookRepository.existsById(bookId)) {
            throw new ResourceNotFoundException("Book with id " + bookId + " does not exist");
        }

        physicalBookRepository.deleteById(bookId);
        return ResponseDto
                .builder()
                .message("Book with id " + bookId + " has been deleted")
                .code(200)
                .success(true)
                .build();
    }

    @Override
    public ResponseDto<List<BookCategoryDto>> getPhysicalBookCategories() {
        List<BookCategoryDto> list = physicalBookRepository.findAllBookCategories()
                .stream()
                .map(bookCategoryMapper::toDto)
                .toList();
        ;
        return ResponseDto.<List<BookCategoryDto>>builder()
                .code(200)
                .success(true)
                .message("OK")
                .data(list)
                .build();
    }
}
























