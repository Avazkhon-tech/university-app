package uz.mu.lms.service.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import uz.mu.lms.dto.DepartmentDto;
import uz.mu.lms.dto.FacultyDto;
import uz.mu.lms.model.Department;
import uz.mu.lms.model.Faculty;

@Mapper(componentModel = "spring", uses = {FacultyMapper.class})
@RequiredArgsConstructor
public abstract class DepartmentMapper implements AbstractMapper<Department, DepartmentDto> {
}


