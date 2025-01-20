package uz.mu.lms.repository.redis;


import org.springframework.data.repository.CrudRepository;
import uz.mu.lms.model.redis.AttendanceChecking;

import java.util.UUID;

public interface AttendanceCheckingRepository extends CrudRepository<AttendanceChecking, UUID> {
}
