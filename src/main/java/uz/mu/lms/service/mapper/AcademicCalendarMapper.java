package uz.mu.lms.service.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mu.lms.dto.AcademicCalendarDto;
import uz.mu.lms.model.AcademicCalendar;

@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class AcademicCalendarMapper implements AbstractMapper<AcademicCalendar, AcademicCalendarDto> {

    @Mapping(target = "departmentId", expression = "java(entity.getDepartment().getId())")
    public abstract AcademicCalendarDto toDto(AcademicCalendar entity);

    @Mapping(target = "department", ignore = true)
    public abstract AcademicCalendar toEntity(AcademicCalendarDto dto);
}


