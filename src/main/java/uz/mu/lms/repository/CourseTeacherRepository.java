package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.CourseTeacher;

@Repository
public interface CourseTeacherRepository extends JpaRepository<CourseTeacher, Integer> {
}
