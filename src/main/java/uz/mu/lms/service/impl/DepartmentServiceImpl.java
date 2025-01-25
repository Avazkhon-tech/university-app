package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.DepartmentDto;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.model.Department;
import uz.mu.lms.model.Faculty;
import uz.mu.lms.repository.DepartmentRepository;
import uz.mu.lms.repository.FacultyRepository;
import uz.mu.lms.service.DepartmentService;
import uz.mu.lms.service.mapper.DepartmentMapper;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentDto> getAllDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable)
                .stream()
                .map(departmentMapper::toDto).toList();
    }

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        Optional<Faculty> faculty = facultyRepository.findById(departmentDto.faculty().id());

        Department entity = departmentMapper.toEntity(departmentDto);

        if (faculty.isPresent()) {
            entity.setFaculty(faculty.get());
        } else {
            throw new ResourceNotFoundException(
                    "Faculty with id %d does not exist"
                    .formatted(departmentDto.faculty().id()));
        }

        entity = departmentRepository.save(entity);

        return departmentMapper.toDto(entity);

    }
}
