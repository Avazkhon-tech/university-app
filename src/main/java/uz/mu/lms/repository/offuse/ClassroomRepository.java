package uz.mu.lms.repository.offuse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.offuse.ClassRoom;

@Repository
public interface ClassroomRepository extends JpaRepository<ClassRoom, Integer> {
}
