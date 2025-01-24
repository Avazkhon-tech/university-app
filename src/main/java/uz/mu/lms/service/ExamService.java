package uz.mu.lms.service;

import uz.mu.lms.dto.ExamCreateDto;
import uz.mu.lms.projection.StudentExamsProjection;

import java.util.List;

public interface ExamService {

    ExamCreateDto createExam(ExamCreateDto examCreateDTO);

    List<StudentExamsProjection> getStudentExams(boolean getRetakes);
}
