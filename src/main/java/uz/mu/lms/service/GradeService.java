package uz.mu.lms.service;

import uz.mu.lms.dto.GradeDto;
import uz.mu.lms.dto.GradeUpdateDto;

import java.util.List;

public interface GradeService {

    List<GradeDto> initializeGrades(Integer lessonId);

    List<GradeDto> updateGrades(List<GradeUpdateDto> gradeUpdateDtoList);
}
