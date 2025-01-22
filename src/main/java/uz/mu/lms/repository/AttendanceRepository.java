package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.Attendance;

import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    @Query("""
                SELECT a
                FROM Attendance a
                WHERE a.student.id = :stId
                AND a.lesson.id = :lId
            """)
    Optional<Attendance> findAttendanceByStudentIdAndLessonId(
            @Param("stId") Integer studentId,
            @Param("lId") Integer lessonId);


    @Query("""
                SELECT COUNT(a) > 0
                FROM Attendance a
                WHERE a.student.id = :stId
                AND a.lesson.id = :lId
            """)
    boolean existsByStudentIdAndLessonId(
            @Param("stId") Integer studentId,
            @Param("lId") Long lessonId
    );


}
