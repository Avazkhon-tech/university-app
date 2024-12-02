package uz.mu.lms.dto;

public record DegreeDto(
    Integer id,
    Integer degreeName,
    String universityName,
    Integer yearOfCompletion
)
{}
