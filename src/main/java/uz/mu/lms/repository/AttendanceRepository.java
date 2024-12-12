package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mu.lms.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {}
