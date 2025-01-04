package uz.mu.lms.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.mu.lms.dto.BookCategoryDto;
import uz.mu.lms.dto.PhysicalBookDto;
import uz.mu.lms.model.BookCategory;
import uz.mu.lms.model.EBook;
import uz.mu.lms.model.PhysicalBook;
import uz.mu.lms.service.PhysicalBookService;

import java.util.List;

@Repository
public interface PhysicalBookRepository extends JpaRepository<PhysicalBook, Integer> {

    @Query("select distinct b.bookCategory as name from PhysicalBook as b")
    List<BookCategory> findAllBookCategories();

    List<PhysicalBook> findAllByBookCategoryId(Integer bookCategoryId, Pageable pageable);

}
