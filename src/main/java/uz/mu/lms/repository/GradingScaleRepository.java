package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.BookCategory;
import uz.mu.lms.model.GradingScale;

@Repository
public interface GradingScaleRepository extends JpaRepository<GradingScale, Integer> {
}
