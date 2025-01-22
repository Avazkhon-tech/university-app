package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.Group;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    List<Group> findAllByDepartmentId(Integer departmentId);

    @Query("""
            SELECT g
            FROM groups g
            JOIN g.lessonTemplates lt
            JOIN Lesson l ON l.lessonTemplate = lt
            WHERE l.id = :lessonId
            """)
    List<Group> findGroupsByLessonId(@Param("lessonId") Integer lessonId);

}
