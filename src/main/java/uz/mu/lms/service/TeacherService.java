package uz.mu.lms.service;

import org.springframework.data.domain.Pageable;
import uz.mu.lms.dto.TeacherDto;
import uz.mu.lms.model.Teacher;

import java.util.List;

public interface TeacherService {

    TeacherDto addTeacher(TeacherDto teacherDto);

    void deleteTeacher(Integer id);

    List<TeacherDto> getTeachers(Pageable pageable);

    Teacher findTeacherById(Integer id);

    List<Teacher> findAllTeachersByIds(List<Integer> ids);
}
