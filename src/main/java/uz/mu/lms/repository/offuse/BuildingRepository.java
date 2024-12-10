package uz.mu.lms.repository.offuse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.offuse.Building;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {
}
