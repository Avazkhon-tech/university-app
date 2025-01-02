package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.Course;
import uz.mu.lms.projection.StudentCourseProjection;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    // retrieves student-specific courses
    @Query("SELECT g.course.id AS courseId, " +
            "g.course.title AS courseTitle, " +
            "CONCAT(g.teacher.user.firstName, ' ', g.teacher.user.lastName) AS teacherFullName, " +
            "CONCAT(:hostAddr, '/course-content/files/', g.course.id) AS contentUrl " +
            "FROM CourseGroup g " +
            "JOIN g.students AS s " +
            "WHERE s.id = :studentId")
    List<StudentCourseProjection> findByStudentId(@Param("studentId") Integer studentId,
                                                  @Param("hostAddr") String hostAddr);
}
