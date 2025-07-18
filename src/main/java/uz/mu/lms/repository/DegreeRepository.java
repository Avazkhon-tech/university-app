package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.Degree;

@Repository
public interface DegreeRepository extends JpaRepository<Degree, Integer> {
}
