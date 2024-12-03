package uz.mu.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;

public record StudentDto(

    Integer id,

    String studentId,

    @Valid
    UserDto userDto
) {

}
