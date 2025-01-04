package uz.mu.lms.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.Borrowing;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Integer> {

    boolean existsByUserIdAndBook_IdAndReturnedAtIsNull(Integer userId, Integer bookId);

    List<Borrowing> findByReturnedAtIsNull(Pageable pageable);

    Optional<Borrowing> findByIdAndReturnedAtIsNull(Integer id);

    Integer countBorrowingByUserIdAndReturnedAtIsNull(Integer userId);
}
