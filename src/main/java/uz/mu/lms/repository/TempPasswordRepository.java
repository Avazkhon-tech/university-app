package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.TempPassword;
import java.util.Optional;

@Repository
public interface TempPasswordRepository extends JpaRepository<TempPassword, Integer> {

    boolean existsByUserId(Integer userId);
    void deleteByUserId(Integer userId);
    Optional<TempPassword> findByUserId(Integer userId);

    void deleteById(Integer userId);
}
