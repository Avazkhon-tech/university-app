package uz.mu.lms.dto;

import java.util.List;

public record TeacherDto(

    Integer id,

    String teacherId,

    UserDto userDto,

    List<DegreeDto>degrees
)
{}
