package uz.mu.lms.service.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import uz.mu.lms.dto.CourseGroupDto;
import uz.mu.lms.dto.TimeslotDto;
import uz.mu.lms.model.CourseGroup;
import uz.mu.lms.model.TimeSlot;

@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class TimeslotMapper implements AbstractMapper<TimeSlot, TimeslotDto> {
}


