package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.LessonGrade;

import java.util.List;

@Repository
public interface LessonGradeRepository extends JpaRepository<LessonGrade, Integer> {

//    @Query("""
//                SELECT COUNT(g) > 0
//                FROM LessonGrade g
//                WHERE g.student.id = :stId
//                AND g.lesson.id = :lId
//            """)
//    boolean existsByStudentIdAndLessonId(
//            @Param("stId") Integer studentId,
//            @Param("lId") Long lessonId
//    );

    List<LessonGrade> findAllByLessonId(Long lessonId);
}
