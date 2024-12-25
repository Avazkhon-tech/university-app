package uz.mu.lms.service.student;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.StudentDto;
import uz.mu.lms.dto.StudentProfileDto;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.exceptions.FileNotSupportedException;
import uz.mu.lms.exceptions.UserAlreadyExistsException;
import uz.mu.lms.exceptions.UserNotFoundException;
import uz.mu.lms.model.Student;
import uz.mu.lms.model.enums.RoleName;
import uz.mu.lms.repository.RoleRepository;
import uz.mu.lms.repository.StudentRepository;
import uz.mu.lms.service.content.ContentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final ContentService contentService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public ResponseEntity<ResponseDto<StudentDto>> addStudent(StudentDto studentDto) {
        if (studentRepository.existsByUser_Username(studentDto.userDto().getUsername())) {
            throw new UserAlreadyExistsException("Student with username " + studentDto.userDto().getUsername() + " already exists");
        }

        if (studentRepository.existsByUser_PhoneNumber(studentDto.userDto().getPhoneNumber())) {
            throw new UserAlreadyExistsException("Student with phone number " + studentDto.userDto().getPhoneNumber() + " already exists");
        }

        if (studentRepository.existsByStudentId(studentDto.studentId())) {
            throw new UserAlreadyExistsException("Student with id " + studentDto.studentId() + " already exists");
        }

        Student student = studentMapper.toEntity(studentDto);
        student.getUser().setPassword(passwordEncoder.encode(studentDto.userDto().getPassword()));
        student.getUser().setRole(roleRepository.findByName(RoleName.ROLE_STUDENT.toString()));
        Student saved = studentRepository.save(student);

        ResponseDto<StudentDto> responseDto = ResponseDto.<StudentDto>builder()
                .code(200)
                .success(true)
                .message("Student added successfully")
                .data(studentMapper.toDto(saved))
                .build();
        return ResponseEntity.ok(responseDto);
    }

    public ResponseEntity<ResponseDto<StudentDto>> uploadProfileImage(MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        String contentType = file.getContentType();
        if (contentType == null || (!contentType.startsWith("image/"))) {
            throw new FileNotSupportedException(
                    "Only image files are allowed");
        }

        Student student = studentRepository.findByUser_Username(username)
                .orElseThrow(() -> new UserNotFoundException("Student with email " + username + " not found"));

        Integer attachmentId = contentService.createContent(file).getId();
        student.setPhotoAttachmentId(attachmentId);
        studentRepository.save(student);
        ResponseDto<StudentDto> responseDto = ResponseDto.<StudentDto>builder()
                .code(200)
                .success(true)
                .message("Profile picture uploaded successfully")
                .build();
        return ResponseEntity.ok(responseDto);
    }

    public ResponseEntity<byte[]> getProfileImage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Student student = studentRepository.findByUser_Username(username)
                .orElseThrow(() -> new UserNotFoundException("Student with email " + username + " not found"));
        if (student.getPhotoAttachmentId() == null) {
            throw new ResourceNotFoundException("User doesn't have a profile picture");
        }
        return contentService.retrieveContent(student.getPhotoAttachmentId());
    }

    public ResponseEntity<ResponseDto<StudentDto>> deleteStudent(Integer id) {
        if (!studentRepository.existsById(id)) {
            throw new UserNotFoundException("Student with id " + id + " does not exist");
        }
        studentRepository.deleteById(id);
        ResponseDto<StudentDto> studentDtoResponseDto = ResponseDto.<StudentDto>builder()
                .message("Student with id " + id + " deleted successfully")
                .success(true)
                .code(200)
                .build();
        return ResponseEntity.ok(studentDtoResponseDto);

    }

    public ResponseEntity<PaginatedResponseDto<List<StudentDto>>> getAllStudents(Pageable pageable) {
        Page<Student> students = studentRepository.findAll(pageable);
        List<StudentDto> studentDtoList = students
                .stream()
                .map(studentMapper::toDto).toList();

        PaginatedResponseDto<List<StudentDto>> paginatedResponseDto = PaginatedResponseDto
                .<List<StudentDto>>builder()
                .code(200)
                .success(true)
                .data(studentDtoList)
                .size(pageable.getPageSize())
                .page(pageable.getPageNumber())
                .build();

        return ResponseEntity.ok(paginatedResponseDto);
    }

    @Override
    public ResponseEntity<ResponseDto<StudentProfileDto>> getProfileInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Student student = studentRepository.findByUser_Username(username)
                .orElseThrow(() -> new UserNotFoundException("Student with email " + username + " not found"));

        ResponseDto<StudentProfileDto> profile = ResponseDto.<StudentProfileDto>builder()
                .code(200)
                .success(true)
                .message("Successfully retrieved student profile info")
                .data(studentMapper.toProfileDtoFromStudent(student))
                .build();

        return ResponseEntity.ok(profile);
    }

    @Override
    public ResponseEntity<ResponseDto<StudentProfileDto>> updateProfileInfo(StudentProfileDto studentProfileDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Student student = studentRepository.findByUser_Username(username)
                .orElseThrow(() -> new UserNotFoundException("Student with email " + username + " not found"));

        Student saved = studentRepository.save(studentMapper.toEntityFromProfileDto(studentProfileDto, student));

        ResponseDto<StudentProfileDto> updated = ResponseDto.<StudentProfileDto>builder()
                .code(200)
                .success(true)
                .message("Successfully updated student profile info")
                .data(studentMapper.toProfileDtoFromStudent(saved))
                .build();

        return ResponseEntity.ok(updated);

    }
}
