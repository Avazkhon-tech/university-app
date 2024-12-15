package uz.mu.lms.service.course;

import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;
import uz.mu.lms.dto.CourseMaterialDto;
import uz.mu.lms.model.Attachment;
import uz.mu.lms.model.CourseMaterial;
import uz.mu.lms.service.mapper.AbstractMapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.*;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CourseMaterialMapper implements AbstractMapper<CourseMaterial, CourseMaterialDto> {

    @Value("${spring.defaultValues.host}")
    String hostAddr;

    @Mapping(target = "contentUrls", source = "attachments", qualifiedByName = "generateUrls")
    public abstract CourseMaterialDto toDto(CourseMaterial courseMaterial);

    @Named("generateUrls")
    public List<String> generateUrls(List<Attachment> attachments) {
        return attachments.stream()
                .map(attachment -> hostAddr + "/course-material/" + attachment.getId())
                .collect(Collectors.toList());
    }
}


