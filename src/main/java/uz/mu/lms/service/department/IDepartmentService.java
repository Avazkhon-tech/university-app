package uz.mu.lms.service.department;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import uz.mu.lms.dto.DepartmentDto;
import uz.mu.lms.dto.PaginatedResponseDto;

import java.util.List;

public interface IDepartmentService {
    ResponseEntity<PaginatedResponseDto<List<DepartmentDto>>> getAllDepartments(Pageable pageable);

    ResponseEntity<DepartmentDto> createDepartment(DepartmentDto departmentDto);
}
