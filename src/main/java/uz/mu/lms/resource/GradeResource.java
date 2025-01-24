package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.GradeDto;
import uz.mu.lms.dto.GradeUpdateDto;
import uz.mu.lms.dto.GroupDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.service.GradeService;
import uz.mu.lms.service.GroupService;

import java.util.List;

@RestController
@RequestMapping("/api/grade")
@RequiredArgsConstructor
public class GradeResource {

    private final GradeService gradeService;

    @GetMapping
    public ResponseDto<List<GradeDto>> initializeGrades(@RequestParam Integer lessonId) {
        return ResponseDto.success(gradeService.initializeGrades(lessonId));
    }


    @PutMapping
    public ResponseDto<List<GradeDto>> updateGrades(@RequestBody List<GradeUpdateDto> gradeUpdateDtoList) {
        return ResponseDto.success(gradeService.updateGrades(gradeUpdateDtoList));
    }
}
