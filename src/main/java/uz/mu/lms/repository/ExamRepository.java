package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.BookCategory;
import uz.mu.lms.model.Exam;
import uz.mu.lms.projection.StudentExamsProjection;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

    @Query("""
        SELECT DISTINCT e.course.title courseTitle,
        e.examType examType,
        e.room.roomNumber roomNumber,
        e.localDateTime examDateTime,
        CONCAT(t.user.firstName, ' ', t.user.lastName) teacherFullName,
        e.fee as examFee
        FROM Exam e
        JOIN e.students s
        JOIN e.teachers t
        WHERE s.id = :studentId
        AND (
                     (:retake = true AND e.examType IN ('MIDTERM_RETAKE', 'FINAL_RETAKE'))
                     OR
                     (:retake = false AND e.examType IN ('MIDTERM', 'FINAL'))
                 )
""")
    List<StudentExamsProjection> findExamsByStudentId(@Param("studentId") Integer studentId, @Param("retake") boolean getRetakeExams);

}
