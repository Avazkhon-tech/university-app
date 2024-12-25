package uz.mu.lms.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record CourseDto(

    Integer id,

    @NotBlank(message = "Course title has to be specified")
    String title,

    String description,

    @NotBlank(message = "The language has to be specified")
    String taughtLanguage,

    @NotBlank(message = "The department has to be specified")
    DepartmentDto department
)
{}
