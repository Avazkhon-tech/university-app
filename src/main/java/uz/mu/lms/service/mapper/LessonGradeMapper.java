package uz.mu.lms.service.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mu.lms.dto.GradeDto;
import uz.mu.lms.dto.GradingScaleDto;
import uz.mu.lms.model.LessonGrade;

@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class LessonGradeMapper implements AbstractMapper<LessonGrade, GradeDto> {

    @Mapping(target = "studentId", expression = "java(entity.getStudent().getStudentId())")
    @Mapping(target = "studentFirstname", expression = "java(entity.getStudent().getUser().getFirstName())")
    @Mapping(target = "studentLastname", expression = "java(entity.getStudent().getUser().getLastName())")
    @Mapping(target = "maxGrade", expression = "java(entity.getCourse().getGradingScale().getProgress())")
    public abstract GradeDto toDto(LessonGrade entity);
}


