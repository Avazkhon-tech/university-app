package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.AcademicCalendarDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.model.AcademicCalendar;
import uz.mu.lms.model.Department;
import uz.mu.lms.repository.AcademicCalendarRepository;
import uz.mu.lms.repository.DepartmentRepository;
import uz.mu.lms.service.AcademicCalendarService;
import uz.mu.lms.service.mapper.AcademicCalendarMapper;

@Service
@RequiredArgsConstructor
public class AcademicCalendarImpl implements AcademicCalendarService {


    private final AcademicCalendarRepository academicCalendarRepository;
    private final AcademicCalendarMapper academicCalendarMapper;
    private final DepartmentRepository departmentRepository;

    public AcademicCalendarDto createAcademicCalendar(AcademicCalendarDto academicCalendarDto) {

        if (academicCalendarRepository.existsByDepartmentId(academicCalendarDto.departmentId())) {
            throw new ResourceNotFoundException("Academic Calendar already exists for this department");
        }

        Department department = departmentRepository.findById(academicCalendarDto.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        AcademicCalendar entity = academicCalendarMapper.toEntity(academicCalendarDto);
        entity.setDepartment(department);

        AcademicCalendar save = academicCalendarRepository.save(entity);

        return academicCalendarMapper.toDto(save);
    }

}
