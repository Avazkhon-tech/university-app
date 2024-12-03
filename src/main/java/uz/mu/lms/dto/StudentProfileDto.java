package uz.mu.lms.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record StudentProfileDto(

    Integer id,

    String studentId,

    String username,

    String firstName,

    String lastName,

    @NotBlank(message = "Personal email field can not be empty")
    String personal_email,

    @NotBlank(message = "Phone number field can not be empty")
    String phoneNumber,

    LocalDate birthDate

) {}
