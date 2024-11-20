package uz.mu.lms.dto;

import jakarta.validation.Valid;

public record StudentDto(

    Integer id,

    String bio,

    String studentId,

    @Valid
    UserDto userDto
) {

}
