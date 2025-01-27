package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.Department;
import uz.mu.lms.model.GPA;
import uz.mu.lms.projection.GpaProjection;

import java.util.List;

@Repository
public interface GpaRepository extends JpaRepository<GPA, Integer> {


    @Query("""
            SELECT g.course.title courseTitle,
            g.course.ECTSCredits credits,
            g.score gpa
            FROM GPA g
            WHERE g.student.id = :studentId
            """)
    List<GpaProjection> findGpaForStudent(@Param("studentId") Integer studentId);
}
