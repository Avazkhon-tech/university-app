package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.projection.ScheduleProjection;
import uz.mu.lms.service.LessonService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lesson")
public class LessonResource {


    private final LessonService lessonService;

    @GetMapping
    public ResponseDto<List<ScheduleProjection>> getAllRooms(@RequestParam LocalDate date) {
        List<ScheduleProjection> lessonsForToday = lessonService.getLessonsForToday(date);
        return ResponseDto.success(lessonsForToday);
    }

    // todo  figure out a better way to solve this problem
    @PostMapping("/generate-lessons")
    public void generateLessons() {
        lessonService.generateLesson();
    }
}
