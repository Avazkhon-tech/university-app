package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.Attachment;
import uz.mu.lms.model.Course;
import uz.mu.lms.model.Student;
import uz.mu.lms.projection.CourseGroupProjection;
import uz.mu.lms.projection.CourseWithTeacherProjection;
import uz.mu.lms.projection.StudentCoursesProjection;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query("SELECT g.course.id AS courseId, " +
           "CONCAT(g.teacher.user.firstName, ' ', g.teacher.user.lastName) AS teacherFullName, " +
           "g.course.title AS courseTitle " +
           "FROM CourseGroup g")
    List<CourseGroupProjection> findByCourseId2(Integer courseId);


    @Query("Select g.course.id as courseId, g.course.title as courseTitle, g.teacher.user.firstName as teacherFullName " +
            "from CourseGroup g join g.students as s where s.id = :studentId")
    List<CourseGroupProjection> findByStudentId(@Param("studentId") Integer studentId);

}
