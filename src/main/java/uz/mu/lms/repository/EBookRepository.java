package uz.mu.lms.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.mu.lms.dto.BookCategoryDto;
import uz.mu.lms.model.BookCategory;
import uz.mu.lms.model.EBook;

import java.util.List;

@Repository
public interface EBookRepository extends JpaRepository<EBook, Integer> {



    @Query("select distinct b.bookCategory as name from EBook as b")
    List<BookCategory> findAllBookCategories();

    List<EBook> findAllByBookCategoryId(Integer bookCategoryId, Pageable pageable);

}
