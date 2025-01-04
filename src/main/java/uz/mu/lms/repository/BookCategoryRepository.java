package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.BookCategory;

import java.util.Optional;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Integer> {

}
