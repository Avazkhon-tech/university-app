package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.BookCategoryDto;
import uz.mu.lms.dto.EBookDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.model.Attachment;
import uz.mu.lms.model.BookCategory;
import uz.mu.lms.model.EBook;
import uz.mu.lms.repository.BookCategoryRepository;
import uz.mu.lms.repository.EBookRepository;
import uz.mu.lms.service.ContentService;
import uz.mu.lms.service.EBookService;
import uz.mu.lms.service.mapper.BookCategoryMapper;
import uz.mu.lms.service.mapper.EBookMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EBookServiceImpl implements EBookService {

    private final EBookRepository eBookRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final ContentService contentService;
    private final EBookMapper eBookMapper;
    private final BookCategoryMapper bookCategoryMapper;


    @Override
    public List<BookCategoryDto> getAllBookCategories() {
        return eBookRepository.findAllBookCategories()
                .stream()
                .map(bookCategoryMapper::toDto)
                .toList();

    }

    public List<EBookDto> getAllEBooks(Pageable pageable, Integer categoryId) {
        return eBookRepository.findAllByBookCategoryId(categoryId, pageable)
                .stream()
                .map(eBookMapper::toDto)
                .toList();
    }

    public EBookDto addEBook(EBookDto eBookDTO, MultipartFile coverImage, MultipartFile bookFile) {

        EBook eBook = eBookMapper.toEntity(eBookDTO);

        Optional<BookCategory> bookCategory = bookCategoryRepository.findById(eBookDTO.bookCategoryId());

        if (bookCategory.isEmpty()) {
            throw new ResourceNotFoundException("Book category not found");
        }

        eBook.setBookCategory(bookCategory.get());

        Attachment coverImageAttachment = contentService.createContent(coverImage);
        Attachment bookFileAttachment = contentService.createContent(bookFile);

        eBook.setBookCoverImage(coverImageAttachment);
        eBook.setBookFile(bookFileAttachment);

        EBook bookSaved = eBookRepository.save(eBook);

        return eBookMapper.toDto(bookSaved);

    }

    @Override
    public void deleteEBook(Integer bookId) {
        if(!eBookRepository.existsById(bookId)) {
            throw new ResourceNotFoundException("Book with id " + bookId + " does not exist");
        }
        eBookRepository.deleteById(bookId);
    }
}
























