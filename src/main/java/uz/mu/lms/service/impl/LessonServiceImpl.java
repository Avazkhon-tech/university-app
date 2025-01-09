package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.exceptions.UserNotFoundException;
import uz.mu.lms.model.Student;
import uz.mu.lms.projection.ScheduleProjection;
import uz.mu.lms.repository.*;
import uz.mu.lms.service.LessonService;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;

    @Override
    public ResponseDto<List<ScheduleProjection>> getLessonsForToday(LocalDate date) {

        System.out.println(LocalDate.now());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Student student = studentRepository.findByUser_Username(username)
                .orElseThrow(() -> new UserNotFoundException("Student not found"));

        List<ScheduleProjection> lessonsByDate = lessonRepository.findLessonsByDate(date, student.getGroup().getId());

        return ResponseDto.<List<ScheduleProjection>>
                builder()
                .code(200)
                .success(true)
                .message("OK")
                .data(lessonsByDate)
                .build();
    }
}
