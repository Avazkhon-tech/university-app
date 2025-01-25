package uz.mu.lms.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.StudentDto;
import uz.mu.lms.dto.StudentProfileDto;
import uz.mu.lms.model.Attachment;
import uz.mu.lms.model.Student;

import java.util.List;


public interface StudentService {

    StudentDto addStudent(StudentDto studentDto);

    void uploadProfileImage(MultipartFile file);

    Attachment getProfileImage();

    void deleteStudent(Integer id);

    List<StudentDto> getAllStudents(Pageable pageable);

    StudentProfileDto getProfileInfo();

    StudentProfileDto updateProfileInfo(StudentProfileDto studentProfileDto);

    List<Student> findStudentsByGroupIds(List<Integer> groupIds);

    Student findCurrentStudent();
}
