package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.Lesson;
import uz.mu.lms.model.LessonTemplate;
import uz.mu.lms.projection.ScheduleProjection;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {


        @Query("""
        SELECT lt.dayOfWeek as dayOfWeek,
               ts.startTime as startTime,
               ts.endTime as endTime,
               c.title as courseTitle,
               r.roomNumber as roomNumber,
               CONCAT(t.user.firstName, ' ', t.user.lastName) as teacherFullName,
               lt.lessonType as lessonType
        FROM Lesson l
        JOIN l.lessonTemplate lt
        JOIN lt.timeSlot ts
        JOIN lt.course c
        JOIN lt.room r
        JOIN lt.teacher t
        JOIN groups g ON lt MEMBER OF g.lessonTemplates
        WHERE l.lessonDate = :currentDate AND g.id = :groupId
        """)
        List<ScheduleProjection> findLessonsByDate(
                @Param("currentDate") LocalDate currentDate,
                @Param("groupId") Integer groupId);


}
