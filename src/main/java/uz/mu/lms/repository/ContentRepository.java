package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.Attachment;

@Repository
public interface ContentRepository extends JpaRepository<Attachment, Integer> {
}
