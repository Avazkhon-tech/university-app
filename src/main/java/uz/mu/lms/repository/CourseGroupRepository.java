package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.CourseGroup;
import uz.mu.lms.projection.CourseGroupProjection;

import java.util.List;

@Repository
public interface CourseGroupRepository extends JpaRepository<CourseGroup, Integer> {

    @Query("SELECT g.course.id AS courseId, " +
           "CONCAT(g.teacher.user.firstName, ' ', g.teacher.user.lastName) AS teacherFullName, " +
           "g.course.title AS courseTitle " +
           "FROM CourseGroup g")
    List<CourseGroupProjection> findByCourseId(Integer courseId);
}
