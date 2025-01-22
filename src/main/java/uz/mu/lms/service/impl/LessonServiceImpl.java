package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.exceptions.UserNotFoundException;
import uz.mu.lms.model.*;
import uz.mu.lms.projection.ScheduleProjection;
import uz.mu.lms.repository.*;
import uz.mu.lms.service.LessonService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;
    private final AcademicCalendarRepository academicCalendarRepository;
    private final GroupRepository groupRepository;

    @Override
    public ResponseDto<List<ScheduleProjection>> getLessonsForToday(LocalDate date) {

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

    @Override
    public void generateLesson() {
        for (AcademicCalendar academicCalendar : academicCalendarRepository.findAllByAreLessonsGeneratedFalse()) {
            generateLessonsForSemester(
                    1,
                    academicCalendar.getDepartment().getId(),
                    academicCalendar.getSem1StartDate(),
                    academicCalendar.getSem1EndDate(),
                    academicCalendar.getSem1MidtermStart(),
                    academicCalendar.getSem1MidtermEnd(),
                    academicCalendar.getSem1FinalStart(),
                    academicCalendar.getSem1FinalEnd());

            generateLessonsForSemester(
                    2,
                    academicCalendar.getDepartment().getId(),
                    academicCalendar.getSem2StartDate(),
                    academicCalendar.getSem2EndDate(),
                    academicCalendar.getSem2MidtermStart(),
                    academicCalendar.getSem2MidtermEnd(),
                    academicCalendar.getSem2FinalStart(),
                    academicCalendar.getSem2FinalEnd());

            academicCalendar.setAreLessonsGenerated(true);
            academicCalendarRepository.save(academicCalendar);
        }
    }

    private void generateLessonsForSemester(Integer semester, Integer departmentId, LocalDate semesterStartDate, LocalDate semesterEndDate,
                                           LocalDate midtermStart, LocalDate midtermEnd, LocalDate finalStart, LocalDate finalEnd) {

        for (Group group : groupRepository.findAllByDepartmentId(departmentId)) {

            for (LessonTemplate lessonTemplate : group.getLessonTemplates()) {

                if (lessonTemplate.getCourse().getSemester() != semester) continue;

                LocalDate lessonDate = getNextWeekDay(lessonTemplate.getDayOfWeek(), semesterStartDate);

                while (!lessonDate.isAfter(semesterEndDate)) {

                    if ((lessonDate.isAfter(midtermStart) && lessonDate.isBefore(midtermEnd)) ||
                            (lessonDate.isAfter(finalStart) && lessonDate.isBefore(finalEnd))
                    ) {
                        lessonDate = getNextWeekDay(lessonTemplate.getDayOfWeek(), lessonDate.plusDays(7));
                        continue;
                    }

                    Lesson lesson = Lesson.builder()
                            .lessonDate(lessonDate)
                            .lessonTemplate(lessonTemplate)
                            .build();

                    lessonRepository.save(lesson);

                    lessonDate = lessonDate.plusDays(7);
                }
            }
        }
    }

    private LocalDate getNextWeekDay(DayOfWeek dayOfWeek, LocalDate currentDate) {
        LocalDate nextLessonDate = currentDate;

        while (nextLessonDate.getDayOfWeek() != dayOfWeek) {
            nextLessonDate = nextLessonDate.plusDays(1);
        }

        return nextLessonDate;
    }

}
