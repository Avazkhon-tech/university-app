package uz.mu.lms.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mu.lms.dto.LessonTemplateDto;
import uz.mu.lms.model.LessonTemplate;

@Mapper(componentModel = "spring")
public abstract class LessonTemplateMapper implements AbstractMapper<LessonTemplate, LessonTemplateDto> {

    @Mapping(target = "teacherId", expression = "java(entity.getTeacher().getId())")
    @Mapping(target = "courseId", expression = "java(entity.getCourse().getId())")
    @Mapping(target = "roomId", expression = "java(entity.getRoom().getId())")
    @Mapping(target = "timeSlotId", expression = "java(entity.getTimeSlot().getId())")
    public abstract LessonTemplateDto toDto(LessonTemplate entity);
}


