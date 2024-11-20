package uz.mu.lms.resource;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.StudentDto;
import uz.mu.lms.service.student.StudentService;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentResource {

    private final StudentService studentService;

    @PostMapping
    public ResponseDto<StudentDto> addStudent(@Valid @RequestBody StudentDto studentDto) {
        return studentService.addStudent(studentDto);
    }

    @PatchMapping("/{id}")
    public ResponseDto<StudentDto> partialUpdateStudent(@PathVariable Integer id, @RequestBody StudentDto studentDto) {
        return studentService.partialUpdateStudent(id, studentDto);
    }

    @PostMapping("/profile")
    public ResponseDto<StudentDto> uploadProfileImage(@RequestParam MultipartFile file) {
        return studentService.uploadProfileImage(file);
    }

    @GetMapping("/profile")
    public ResponseEntity<byte[]> getProfileImage() {
        return studentService.getProfileImage();
    }



//    @GetMapping
//    public PaginatedResponseDto<List<UserDto>> getAllUsers(
//            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
//        return userService.getAllUsers(page, size);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseDto<UserDto> getUserById(@PathVariable Integer id) {
//        return userService.getUser(id);
//    }

}
