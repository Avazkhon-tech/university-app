package uz.mu.lms.repository.redis;


import org.springframework.data.repository.CrudRepository;
import uz.mu.lms.model.redis.AttendanceChecking;

public interface AttendanceCheckingRepository extends CrudRepository<AttendanceChecking, Integer> {
}
