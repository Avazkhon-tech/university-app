package uz.mu.lms.dto;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.Date;

public record StudentProfileDto(

    Integer id,

    String studentId,

    String firstName,

    String lastName,

    String personal_email,

    String phoneNumber,

    LocalDate birthDate

) {}
