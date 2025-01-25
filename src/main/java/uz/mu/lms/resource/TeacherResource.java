package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.TeacherDto;
import uz.mu.lms.service.TeacherService;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherResource {

    private final TeacherService teacherService;


    // ONLY FOR ADMINS
    @PostMapping
    public ResponseDto<TeacherDto> addTeacher(@RequestBody TeacherDto teacherDto) {
        return ResponseDto.success(teacherService.addTeacher(teacherDto));
    }

    @DeleteMapping("/{id}")
    public ResponseDto<?> deleteTeacher(@PathVariable Integer id) {
        return ResponseDto.success("Teacher has been deleted");
    }

    @GetMapping
    public PaginatedResponseDto<List<TeacherDto>> getTeachers(
            @PageableDefault Pageable pageable) {
        List<TeacherDto> teachers = teacherService.getTeachers(pageable);
        return PaginatedResponseDto.success(pageable.getPageNumber(), teachers.size(), teachers);
    }
}

