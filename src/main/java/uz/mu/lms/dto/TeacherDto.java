package uz.mu.lms.dto;

import java.util.List;
import java.util.Set;

public record TeacherDto(

    Integer id,

    String teacherId,

    UserDto userDto,

    List<DegreeDto>degrees,

    Set<DepartmentDto> departments
)
{}
