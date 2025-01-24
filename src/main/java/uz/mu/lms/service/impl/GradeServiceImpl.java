package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mu.lms.dto.GradeDto;
import uz.mu.lms.dto.GradeUpdateDto;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.model.*;
import uz.mu.lms.model.enums.LessonGradingType;
import uz.mu.lms.repository.*;
import uz.mu.lms.service.GradeService;
import uz.mu.lms.service.mapper.LessonGradeMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final LessonGradeRepository lessonGradeRepository;
    private final LessonRepository lessonRepository;
    private final GroupRepository groupRepository;
    private final LessonGradeMapper lessonGradeMapper;

    @Transactional
    @Override
    public List<GradeDto> initializeGrades(Integer lessonId) {
        Lesson lesson = getLessonById(lessonId);
        List<Group> groups = groupRepository.findGroupsByLessonId(lessonId);

        List<LessonGrade> lessonGrades = lessonGradeRepository.findAllByLessonId(lesson.getId());

        if (lessonGrades.isEmpty()) {
            groups.forEach(group -> group.getStudents().forEach(student -> {
                LessonGrade lessonGrade = LessonGrade.builder()
                        .course(lesson.getLessonTemplate().getCourse())
                        .gradingType(LessonGradingType.ASSIGNMENT)
                        .student(student)
                        .lesson(lesson)
                        .comment("")
                        .grade(0)
                        .build();
                lessonGrades.add(lessonGrade);
            }));
            lessonGradeRepository.saveAll(lessonGrades);
        }

        return lessonGrades.stream().map(lessonGradeMapper::toDto).toList();
    }

    @Override
    public List<GradeDto> updateGrades(List<GradeUpdateDto> gradeUpdateDtoList) {

        List<Integer> gradeIds = gradeUpdateDtoList.stream()
                .map(GradeUpdateDto::id)
                .toList();

        List<LessonGrade> lessonGrades = lessonGradeRepository.findAllById(gradeIds);

        Map<Integer, LessonGrade> gradeMap = lessonGrades.stream()
                .collect(Collectors.toMap(LessonGrade::getId, Function.identity()));

        gradeUpdateDtoList.forEach(gradeUpdateDto -> {
            LessonGrade lessonGrade = gradeMap.get(gradeUpdateDto.id());
            if (lessonGrade != null) {
                Integer progress = lessonGrade.getCourse().getGradingScale().getProgress();
                lessonGrade.setGrade(Math.min(gradeUpdateDto.grade(), progress));
                lessonGrade.setComment(gradeUpdateDto.comment());
            }
        });

        lessonGradeRepository.saveAll(lessonGrades);

        return lessonGrades.stream()
                .map(lessonGradeMapper::toDto)
                .toList();
    }


    private Lesson getLessonById(Integer lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson with id %s not found".formatted(lessonId)));
    }
}
