package uz.mu.lms.resource;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.StudentDto;
import uz.mu.lms.dto.StudentProfileDto;
import uz.mu.lms.model.Attachment;
import uz.mu.lms.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentResource {

    private final StudentService studentService;

    // ONLY FOR ADMINS
    @GetMapping
    public PaginatedResponseDto<List<StudentDto>> getAllStudents(@PageableDefault Pageable pageable) {
        List<StudentDto> students = studentService.getAllStudents(pageable);
        return PaginatedResponseDto.success(pageable.getPageNumber(), students.size(), students);
    }

    @PostMapping
    public ResponseDto<StudentDto> addStudent(@Valid @RequestBody StudentDto studentDto) {
        return ResponseDto.success(studentService.addStudent(studentDto));
    }

    @DeleteMapping("/{id}")
    public ResponseDto<Object> deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
        return ResponseDto.success("Student has been deleted successfully");
    }

    // FOR ALL
    @PostMapping("/profile-picture")
    public ResponseDto<Object> uploadProfileImage(@RequestParam MultipartFile file) {
        studentService.uploadProfileImage(file);
        return ResponseDto.success("Profile photo has been uploaded successfully");
    }

    @GetMapping("/profile-picture")
    public ResponseEntity<byte[]> getProfileImage() {
        Attachment attachment = studentService.getProfileImage();
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        attachment.getFilename().substring(attachment.getFilename().indexOf('_') + 1) + "\"")
                .body(attachment.getBytes());
    }

    @GetMapping("/profile-info")
    public ResponseDto<StudentProfileDto> getProfileInfo() {
        return ResponseDto.success(studentService.getProfileInfo());
    }

    @PostMapping("/profile-info")
    public ResponseDto<StudentProfileDto> updateProfileInfo(@Valid @RequestBody StudentProfileDto studentProfileDto) {
        return ResponseDto.success(studentService.updateProfileInfo(studentProfileDto));
    }
}
