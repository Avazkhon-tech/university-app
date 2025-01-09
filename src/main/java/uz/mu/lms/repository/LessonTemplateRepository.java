package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.LessonTemplate;

import java.util.List;

@Repository
public interface LessonTemplateRepository extends JpaRepository<LessonTemplate, Integer> {

    List<LessonTemplate> findByGroupId(Integer groupId);
}
