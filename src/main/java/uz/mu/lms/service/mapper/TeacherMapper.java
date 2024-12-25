package uz.mu.lms.service.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mu.lms.dto.TeacherDto;
import uz.mu.lms.model.Teacher;

@Mapper(componentModel = "spring", uses = {UserMapper.class, DegreeMapper.class, DepartmentMapper.class})
@RequiredArgsConstructor
public abstract class TeacherMapper implements AbstractMapper<Teacher, TeacherDto> {

    @Mapping(target = "user", source = "userDto")
    @Mapping(target = "degrees", ignore = true)
    public abstract Teacher toEntity(TeacherDto dto);

    @Mapping(target = "userDto", source = "user")
    public abstract TeacherDto toDto(Teacher entity);
}


