package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.AcademicCalendar;

import java.util.List;

@Repository
public interface AcademicCalendarRepository extends JpaRepository<AcademicCalendar, Integer> {

    boolean existsByDepartmentId(Integer departmentId);

   List<AcademicCalendar> findAllByAreLessonsGeneratedFalse();
}
