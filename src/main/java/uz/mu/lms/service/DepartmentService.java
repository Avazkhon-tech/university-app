package uz.mu.lms.service;

import org.springframework.data.domain.Pageable;
import uz.mu.lms.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDto> getAllDepartments(Pageable pageable);

    DepartmentDto createDepartment(DepartmentDto departmentDto);
}
