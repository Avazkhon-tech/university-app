package uz.mu.lms.service.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mu.lms.dto.CourseDto;
import uz.mu.lms.model.Course;

@Mapper(componentModel = "spring", uses = GradingScaleMapper.class)
@RequiredArgsConstructor
public abstract class CourseMapper implements AbstractMapper<Course, CourseDto> {

    @Mapping(target = "department", ignore = true)
    public abstract Course toEntity(CourseDto dto);

    @Mapping(target = "departmentId", expression = "java(entity.getDepartment().getId())")
    public abstract CourseDto toDto(Course entity);
}


