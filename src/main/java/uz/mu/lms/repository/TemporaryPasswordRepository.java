package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mu.lms.model.TemporaryPassword;

import java.util.Optional;


public interface TemporaryPasswordRepository extends JpaRepository<TemporaryPassword, Long> {

    boolean existsByUserId(Long userId);
    void deleteByUserId(Long userId);
    Optional<TemporaryPassword> findByUserId(Long userId);
}
