package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.ExamCreateDto;
import uz.mu.lms.model.Exam;
import uz.mu.lms.model.Student;
import uz.mu.lms.projection.StudentExamsProjection;
import uz.mu.lms.repository.ExamRepository;
import uz.mu.lms.service.ExamService;
import uz.mu.lms.service.StudentService;
import uz.mu.lms.service.mapper.ExamMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {


    private final ExamRepository examRepository;
    private final ExamMapper examMapper;
    private final StudentService studentService;

    @Override
    public ExamCreateDto createExam(ExamCreateDto examCreateDTO) {
        Exam entity = examMapper.toEntity(examCreateDTO);
        Exam save = examRepository.save(entity);
        return examMapper.toDto(save);
    }

    @Override
    public List<StudentExamsProjection> getStudentExams(boolean getRetakes) {
        Student currentStudent = studentService.findCurrentStudent();
        return examRepository.findExamsByStudentId(currentStudent.getId(), getRetakes);
    }
}
