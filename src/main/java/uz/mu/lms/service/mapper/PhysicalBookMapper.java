package uz.mu.lms.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mu.lms.dto.EBookDto;
import uz.mu.lms.dto.PhysicalBookDto;
import uz.mu.lms.model.EBook;
import uz.mu.lms.model.PhysicalBook;

@Mapper(componentModel = "spring", uses = AttachmentMapper.class)
public abstract class PhysicalBookMapper implements AbstractMapper<PhysicalBook, PhysicalBookDto> {

    @Mapping(target = "bookCategoryId", expression = "java(entity.getBookCategory().getId())")
    public abstract PhysicalBookDto toDto(PhysicalBook entity);
}


