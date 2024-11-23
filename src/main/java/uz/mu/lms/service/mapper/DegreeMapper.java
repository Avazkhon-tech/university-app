package uz.mu.lms.service.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import uz.mu.lms.dto.DegreeDto;
import uz.mu.lms.model.Degree;

@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class DegreeMapper implements AbstractMapper<Degree, DegreeDto> {

}


