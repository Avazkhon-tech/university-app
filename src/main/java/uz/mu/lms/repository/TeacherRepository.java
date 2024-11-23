package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.Teacher;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    Optional<Teacher> findByUser_Username(String username);

    boolean existsByUser_Username(String username);

    boolean existsByUser_PhoneNumber(String phoneNumber);

    boolean existsByTeacherId(String studentId);

}
