package uz.mu.lms.service;

import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.projection.ScheduleProjection;

import java.time.LocalDate;
import java.util.List;

public interface LessonService {

    ResponseDto<List<ScheduleProjection>> getLessonsForToday(LocalDate date);

    void generateLesson();
}
