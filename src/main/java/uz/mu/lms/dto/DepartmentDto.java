package uz.mu.lms.dto;

public record DepartmentDto(
    Integer id,

    String name,

    Long tuitionFee,

    FacultyDto faculty
) {}
