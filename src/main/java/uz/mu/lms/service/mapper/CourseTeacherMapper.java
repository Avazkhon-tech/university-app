package uz.mu.lms.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mu.lms.dto.CourseTeacherDto;
import uz.mu.lms.model.CourseTeacher;

@Mapper(componentModel = "spring")
public abstract class CourseTeacherMapper implements AbstractMapper<CourseTeacher, CourseTeacherDto> {

    @Mapping(target = "teacherId", expression = "java(entity.getTeacher().getId())")
    @Mapping(target = "courseId", expression = "java(entity.getCourse().getId())")
    public abstract CourseTeacherDto toDto(CourseTeacher entity);
}


