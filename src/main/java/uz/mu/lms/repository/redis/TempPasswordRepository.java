package uz.mu.lms.repository.redis;


import org.springframework.data.repository.CrudRepository;
import uz.mu.lms.model.redis.TempPassword;

public interface TempPasswordRepository extends CrudRepository<TempPassword, Integer> {
}
