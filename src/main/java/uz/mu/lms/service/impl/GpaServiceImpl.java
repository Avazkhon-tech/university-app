package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.AcademicCalendarDto;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.model.AcademicCalendar;
import uz.mu.lms.model.Department;
import uz.mu.lms.model.Student;
import uz.mu.lms.projection.GpaProjection;
import uz.mu.lms.repository.AcademicCalendarRepository;
import uz.mu.lms.repository.DepartmentRepository;
import uz.mu.lms.repository.GpaRepository;
import uz.mu.lms.repository.StudentRepository;
import uz.mu.lms.service.AcademicCalendarService;
import uz.mu.lms.service.GpaService;
import uz.mu.lms.service.StudentService;
import uz.mu.lms.service.mapper.AcademicCalendarMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GpaServiceImpl implements GpaService {


    private final GpaRepository gpaRepository;
    private final StudentRepository studentRepository;

    @Override
    public List<GpaProjection> getAllGpa() {

        Student student = studentRepository.findCurrentStudent();

        return gpaRepository.findGpaForStudent(student.getId());

    }
}
