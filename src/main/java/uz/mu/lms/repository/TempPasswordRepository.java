package uz.mu.lms.repository;


import org.springframework.data.repository.CrudRepository;
import uz.mu.lms.redis.TempPassword;

public interface TempPasswordRepository extends CrudRepository<TempPassword, Integer> {
}
