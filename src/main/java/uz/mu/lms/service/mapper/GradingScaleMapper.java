package uz.mu.lms.service.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mu.lms.dto.CourseDto;
import uz.mu.lms.dto.GradingScaleDto;
import uz.mu.lms.model.Course;
import uz.mu.lms.model.GradingScale;

@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class GradingScaleMapper implements AbstractMapper<GradingScale, GradingScaleDto> {
}


