package uz.mu.lms.dto;

public record GradeDto(

    Integer id,
    String studentId,
    String studentFirstname,
    String studentLastname,
    Integer grade,
    Integer maxGrade,
    String comment
    )
{}
