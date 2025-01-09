package uz.mu.lms.dto;

public record LessonTemplateDto(

         Integer id,

         Integer teacherId,

         Integer courseId,

         Integer timeSlotId,

         Integer roomId,

         String dayOfWeek,

         String lessonType
) {}
