package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.Student;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByUser_Username(String username);

    boolean existsByUser_Username(String username);

    boolean existsByUser_PhoneNumber(String phoneNumber);

    boolean existsByStudentId(String studentId);

}
