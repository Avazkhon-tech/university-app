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
    @Query(
    "SELECT DISTINCT c.course.id AS courseId, " +
            "c.course.title AS courseTitle, " +
            "CONCAT(:hostAddr, '/api/course-content/files/', c.course.id) AS contentUrl, " +
            "CONCAT(c.teacher.user.firstName, ' ' , c.teacher.user.lastName) AS teacherFullName " +
            "FROM Student s " +
            "JOIN s.group g " +
            "JOIN g.lessonTemplates c " +
            "WHERE s.id = :studentId")
    List<StudentCourseProjection> findByStudentId(
    @Param("studentId") Integer studentId,
    @Param("hostAddr") String hostAddr);
}
