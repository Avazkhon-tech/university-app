package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.Attachment;
import uz.mu.lms.model.Faculty;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
}
