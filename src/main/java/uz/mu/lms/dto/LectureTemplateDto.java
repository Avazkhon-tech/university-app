package uz.mu.lms.dto;

import java.util.List;

public record LectureTemplateDto (

         Integer id,

         Integer teacherId,

         Integer courseId,

         Integer timeSlotId,

         Integer roomId,

         String dayOfWeek,

         String lessonType,

         List<Integer> groupIds

) {}
