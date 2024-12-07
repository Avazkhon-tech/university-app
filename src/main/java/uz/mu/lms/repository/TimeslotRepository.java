package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.Lesson;

@Repository
public interface TimeslotRepository extends JpaRepository<Lesson, Integer> {
}
