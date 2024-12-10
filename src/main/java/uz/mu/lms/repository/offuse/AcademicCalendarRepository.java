package uz.mu.lms.repository.offuse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.AcademicCalendar;

@Repository
public interface AcademicCalendarRepository extends JpaRepository<AcademicCalendar, Integer> {
}
