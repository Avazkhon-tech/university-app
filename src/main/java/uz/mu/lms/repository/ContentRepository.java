package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
}
