package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mu.lms.model.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByPhoneNumber(String phoneNumber);
}
