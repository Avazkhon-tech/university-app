package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.CourseGroup;
import uz.mu.lms.projection.StudentCourseProjection;

import java.util.List;

@Repository
public interface CourseGroupRepository extends JpaRepository<CourseGroup, Integer> {

}
