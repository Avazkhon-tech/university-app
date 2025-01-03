package uz.mu.lms.service.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uz.mu.lms.dto.StudentDto;
import uz.mu.lms.dto.StudentProfileDto;
import uz.mu.lms.model.Student;

@Mapper(componentModel = "spring", uses = UserMapper.class)
@RequiredArgsConstructor
public abstract class StudentMapper implements AbstractMapper<Student, StudentDto> {

    @Mapping(target = "user", source = "userDto")
    public abstract Student toEntity(StudentDto dto);

    @Mapping(target = "userDto", source = "user")
    public abstract StudentDto toDto(Student entity);

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "personal_email", source = "user.personal_email")
    @Mapping(target = "phoneNumber", source = "user.phoneNumber")
    @Mapping(target = "birthDate", source = "user.birthDate")
    @Mapping(target = "username", source = "user.username")
    public abstract StudentProfileDto toProfileDtoFromStudent(Student student);

    public Student toEntityFromProfileDto(StudentProfileDto dto, @MappingTarget Student student) {
        student.getUser().setPersonal_email(dto.personal_email());
        student.getUser().setPhoneNumber(dto.phoneNumber());
        return student;
    }
}


