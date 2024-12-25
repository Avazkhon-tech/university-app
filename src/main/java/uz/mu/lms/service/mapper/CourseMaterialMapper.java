package uz.mu.lms.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;
import uz.mu.lms.dto.CourseMaterialDto;
import uz.mu.lms.model.Attachment;
import uz.mu.lms.model.CourseMaterial;

import java.util.List;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {AttachmentMapper.class})
public abstract class CourseMaterialMapper implements AbstractMapper<CourseMaterial, CourseMaterialDto> {

}


