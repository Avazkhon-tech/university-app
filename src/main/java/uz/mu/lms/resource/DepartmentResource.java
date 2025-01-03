package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.DepartmentDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentResource {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<List<DepartmentDto>>> getDepartments(@PageableDefault Pageable pageable) {
        return departmentService.getAllDepartments(pageable);
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto) {
        return departmentService.createDepartment(departmentDto);
    }
}
