package uz.mu.lms.dto;

import uz.mu.lms.model.enums.ExamType;

import java.time.LocalDateTime;
import java.util.List;

public record ExamCreateDto(
        Integer id,
        LocalDateTime localDateTime,
        Integer durationMinutes,
        Integer courseId,
        List<Integer> teacherIds,
        Integer roomId,
        ExamType examType,
        Integer fee,
        List<Integer> groupIds
) {}
