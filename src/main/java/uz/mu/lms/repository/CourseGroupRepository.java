package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.CourseGroup;
import uz.mu.lms.projection.StudentCourseProjection;

import java.util.List;

@Repository
public interface CourseGroupRepository extends JpaRepository<CourseGroup, Integer> {

    @Query("SELECT g.course.id AS courseId, " +
           "CONCAT(g.teacher.user.firstName, ' ', g.teacher.user.lastName) AS teacherFullName, " +
           "g.course.title AS courseTitle, " +
           "CONCAT(:hostAddr, '/courses/student/', g.course.id) AS courseUrl " +
           "FROM CourseGroup g " +
           "WHERE g.course.id = :courseId")
    List<StudentCourseProjection> findByCourseId(@Param("courseId") Integer courseId,
                                                 @Param("hostAddr") String hostAddr);
}
