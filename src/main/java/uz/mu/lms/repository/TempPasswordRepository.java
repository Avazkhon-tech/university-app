package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.TempPassword;
import java.util.Optional;

@Repository
public interface TempPasswordRepository extends JpaRepository<TempPassword, Long> {

    boolean existsByUserId(Long userId);
    void deleteByUserId(Long userId);
    Optional<TempPassword> findByUserId(Long userId);
}
