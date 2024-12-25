package uz.mu.lms.service.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mu.lms.dto.FacultyDto;
import uz.mu.lms.model.Faculty;

@Mapper(componentModel = "spring", uses = {TeacherMapper.class})
@RequiredArgsConstructor
public abstract class FacultyMapper implements AbstractMapper<Faculty, FacultyDto> {
}


