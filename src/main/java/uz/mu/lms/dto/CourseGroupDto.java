package uz.mu.lms.dto;

public record CourseGroupDto(
        String name,
        Integer teacherId,
        Integer teacherAssistantId
) {
}
