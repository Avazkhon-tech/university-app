package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.Attachment;
import uz.mu.lms.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
}
