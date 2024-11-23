package uz.mu.lms.resource;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.StudentDto;
import uz.mu.lms.service.student.IStudentService;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentResource {

    private final IStudentService studentService;

    // ONLY FOR ADMINS
    @GetMapping
    public ResponseEntity<PaginatedResponseDto<List<StudentDto>>> getAllStudents(
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return studentService.getAllStudents(pageable);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<StudentDto>> addStudent(@Valid @RequestBody StudentDto studentDto) {
        return studentService.addStudent(studentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<StudentDto>> deleteStudent(@PathVariable Integer id) {
        return studentService.deleteStudent(id);
    }

    // FOR ALL
    @PostMapping("/profile")
    public ResponseEntity<ResponseDto<StudentDto>> uploadProfileImage(@RequestParam MultipartFile file) {
        return studentService.uploadProfileImage(file);
    }

    @GetMapping("/profile")
    public ResponseEntity<byte[]> getProfileImage() {
        return studentService.getProfileImage();
    }
}
