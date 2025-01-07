package uz.mu.lms.dto;

public record GradingScaleDto(

    Integer id,

    Integer pass,

    Integer merit,

    Integer distinction,

    Integer attendance,

    Integer progress,

    Integer midterm,

    Integer finalExam
) {
}