package uz.mu.lms.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import uz.mu.lms.dto.ScoreDto;
import uz.mu.lms.dto.StudentDto;
import uz.mu.lms.dto.StudentProfileDto;
import uz.mu.lms.model.Attachment;
import uz.mu.lms.model.Student;

import java.util.List;
import java.util.Map;


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

    Map<String, ScoreDto> getCourseGrades(Integer courseId);
}
