package uz.mu.lms.service.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mu.lms.dto.StudentDto;
import uz.mu.lms.model.Student;

@Mapper(componentModel = "spring", uses = UserMapper.class)
@RequiredArgsConstructor
public abstract class StudentMapper implements AbstractMapper<Student, StudentDto> {

    @Mapping(target = "user", source = "userDto")
    public abstract Student toEntity(StudentDto dto);

    @Mapping(target = "userDto", source = "user")
    public abstract StudentDto toDto(Student entity);
}


