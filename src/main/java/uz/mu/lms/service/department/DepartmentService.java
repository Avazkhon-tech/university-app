package uz.mu.lms.service.department;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.DepartmentDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.exceptions.ContentDoesNotExistException;
import uz.mu.lms.model.Department;
import uz.mu.lms.model.Faculty;
import uz.mu.lms.repository.DepartmentRepository;
import uz.mu.lms.repository.FacultyRepository;
import uz.mu.lms.service.mapper.DepartmentMapper;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService {

    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public ResponseEntity<PaginatedResponseDto<List<DepartmentDto>>> getAllDepartments(Pageable pageable) {
        List<DepartmentDto> list = departmentRepository.findAll(pageable)
                .stream()
                .map(departmentMapper::toDto).toList();

        var response = PaginatedResponseDto
                .<List<DepartmentDto>>builder()
                .code(200)
                .message("Successfully retrieved departments")
                .success(true)
                .data(list)
                .size(list.size())
                .page(pageable.getPageNumber() + 1)
                .build();

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<DepartmentDto> createDepartment(DepartmentDto departmentDto) {
        Optional<Faculty> faculty = facultyRepository.findById(departmentDto.faculty().id());

        Department entity = departmentMapper.toEntity(departmentDto);

        if (faculty.isPresent()) {
            entity.setFaculty(faculty.get());
        } else {
            throw new ContentDoesNotExistException(
                    "Faculty with id %d does not exist"
                    .formatted(departmentDto.faculty().id()));
        }

        entity = departmentRepository.save(entity);

        DepartmentDto dto = departmentMapper.toDto(entity);

        return ResponseEntity.ok(dto);
    }
}
