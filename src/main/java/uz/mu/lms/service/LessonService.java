package uz.mu.lms.service;

import uz.mu.lms.projection.ScheduleProjection;

import java.time.LocalDate;
import java.util.List;

public interface LessonService {

    List<ScheduleProjection> getLessonsForToday(LocalDate date);

    void generateLesson();

    void initializeLesson(Integer lessonId);
}
