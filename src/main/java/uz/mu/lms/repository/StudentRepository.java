package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.Course;
import uz.mu.lms.model.Student;
import uz.mu.lms.projection.CourseGradeProjection;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByUser_Username(String username);

    boolean existsByUser_Username(String username);

    boolean existsByUser_PhoneNumber(String phoneNumber);

    boolean existsByStudentId(String studentId);

    @Query("""
                  SELECT s FROM Student s
                  JOIN s.group g
                  WHERE g.id IN :groupIds
            """)
    List<Student> findStudentsByGroupIds(@Param("groupIds") List<Integer> groupIds);

    @Query("""
            SELECT
                (SELECT COUNT(s) FROM Student s JOIN s.attendance att
                 WHERE s.id = :studentId AND att.course.id = :courseId AND att.status = 'PRESENT') AS attendancePresent,

                (SELECT COUNT(s) FROM Student s JOIN s.attendance att
                 WHERE s.id = :studentId AND att.course.id = :courseId) AS attendanceTotal,

                COALESCE(
                    (SELECT AVG(lg.grade) FROM LessonGrade lg
                     WHERE lg.course.id = :courseId AND lg.student.id = :studentId), 0
                ) AS progress,

                COALESCE(
                    (SELECT eg.grade FROM ExamGrade eg
                     WHERE eg.student.id = :studentId AND eg.course.id = :courseId AND eg.examType = 'MIDTERM'), 0
                ) AS midterm,

                COALESCE(
                    (SELECT eg.grade FROM ExamGrade eg
                     WHERE eg.student.id = :studentId AND eg.course.id = :courseId AND eg.examType = 'FINAL'), 0
                ) AS final
            """)
    CourseGradeProjection findCourseGradeByStudentId(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);
}
