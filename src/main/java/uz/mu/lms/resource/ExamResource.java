package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.ExamCreateDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.projection.StudentExamsProjection;
import uz.mu.lms.service.ExamService;

import java.util.List;

@RestController
@RequestMapping("/api/exam")
@RequiredArgsConstructor
public class ExamResource {

    private final ExamService examService;

    @PostMapping
    public ResponseDto<?> createExam(@RequestBody ExamCreateDto examCreateDTO) {
        ExamCreateDto examCreateDto = examService.createExam(examCreateDTO);
        return ResponseDto.success(examCreateDto);
    }

    @GetMapping
    public ResponseDto<List<StudentExamsProjection>> getExams(@RequestParam(required = false) boolean getRetakes) {
        return ResponseDto.success(examService.getStudentExams(getRetakes));
    }
}
