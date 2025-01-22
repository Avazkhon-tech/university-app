package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.Attendance;
import uz.mu.lms.projection.AttendanceSummaryProjection;

import java.util.List;
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

    @Query("""
            SELECT a.course as courseId,
                   a.course.title AS courseTitle,
                   COUNT(a) AS totalLessons,
                   SUM(CASE WHEN a.status = 'ABSENT' THEN 1 ELSE 0 END) AS absentCount,
                   (CAST(SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) AS double) / COUNT(a)) * 100 AS percentage
            FROM Attendance a
            WHERE a.student.id = :studentId
            GROUP BY courseId
            """)
    List<AttendanceSummaryProjection> findAttendanceSummaryByCourseForStudent(@Param("studentId") Integer studentId);

}
