package uz.mu.lms.dto;

public record CourseGroupDto(
        String name,
        String title,
        String description,
        String taughtLanguage,
        Integer teacherId,
        Integer teacherAssistantId
) {
}
