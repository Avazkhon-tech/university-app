package uz.mu.lms.dto;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record FacultyDto(

        Integer id,

        @NotBlank(message = "Faculty name can not be empty")
        String name,

        String phoneNumber,

        LocalDate establishmentDate
) {}
