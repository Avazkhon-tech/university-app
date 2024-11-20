package uz.mu.lms.service.student;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.StudentDto;
import uz.mu.lms.exceptions.UserNotFoundException;
import uz.mu.lms.model.Student;
import uz.mu.lms.repository.StudentRepository;
import uz.mu.lms.repository.UserRepository;
import uz.mu.lms.service.content.ContentService;
import uz.mu.lms.service.mapper.StudentMapper;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final ContentService contentService;
    private final UserRepository userRepository;

    @Value("${host}")
    private String hostAddr;

    public ResponseDto<StudentDto> addStudent(StudentDto studentDto) {
        Student student = studentMapper.toEntity(studentDto);

        Student saved = studentRepository.save(student);

        return ResponseDto.<StudentDto>builder()
                .code(200)
                .success(true)
                .message("Student added successfully")
                .data(studentMapper.toDto(saved))
                .build();
    }

    public ResponseDto<StudentDto> partialUpdateStudent(Integer id, StudentDto studentDto) {
        return null;
    }

    public ResponseDto<StudentDto> uploadProfileImage(MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Student student = studentRepository.findByUser_Username(username)
                .orElseThrow(() -> new UserNotFoundException("Student with email " + username + " not found"));

        Integer attachmentId = contentService.createContent(file);
        student.setPhotoAttachmentId(attachmentId);
        studentRepository.save(student);
        return ResponseDto.<StudentDto>builder()
                .code(200)
                .success(true)
                .message("Profile picture uploaded successfully")
                .build();
    }

    public ResponseEntity<byte[]> getProfileImage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Student student = studentRepository.findByUser_Username(username)
                .orElseThrow(() -> new UserNotFoundException("Student with email " + username + " not found"));
        return contentService.retrieveContent(student.getPhotoAttachmentId());
    }
}
