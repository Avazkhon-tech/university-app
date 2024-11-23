package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.TeacherDto;
import uz.mu.lms.service.teacher.ITeacherService;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherResource {

    private final ITeacherService teacherService;


    // ONLY FOR ADMINS
    @PostMapping
    public ResponseEntity<ResponseDto<TeacherDto>> addTeacher(@RequestBody TeacherDto teacherDto) {
        return teacherService.addTeacher(teacherDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> deleteTeacher(@PathVariable Integer id) {
        return teacherService.deleteTeacher(id);
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<List<TeacherDto>>> getTeachers(
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return teacherService.getTeachers(pageable);
    }
}

