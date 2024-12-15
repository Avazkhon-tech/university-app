package uz.mu.lms.service.student;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.StudentDto;
import uz.mu.lms.dto.StudentProfileDto;

import java.util.List;


public interface StudentService {


    // TODO separate business logic from controller
    ResponseEntity<ResponseDto<StudentDto>> addStudent(StudentDto studentDto);

    ResponseEntity<ResponseDto<StudentDto>> uploadProfileImage(MultipartFile file);

    ResponseEntity<byte[]> getProfileImage();

    ResponseEntity<ResponseDto<StudentDto>> deleteStudent(Integer id);

    ResponseEntity<PaginatedResponseDto<List<StudentDto>>> getAllStudents(Pageable pageable);

    ResponseEntity<ResponseDto<StudentProfileDto>> getProfileInfo();

    ResponseEntity<ResponseDto<StudentProfileDto>> updateProfileInfo(StudentProfileDto studentProfileDto);


}
