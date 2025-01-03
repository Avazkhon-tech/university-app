package uz.mu.lms.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.TeacherDto;

import java.util.List;

public interface TeacherService {

    ResponseEntity<ResponseDto<TeacherDto>> addTeacher(TeacherDto teacherDto);

    ResponseEntity<ResponseDto<?>> deleteTeacher(Integer id);

    ResponseEntity<PaginatedResponseDto<List<TeacherDto>>> getTeachers(Pageable pageable);
}
