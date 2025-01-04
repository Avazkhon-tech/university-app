package uz.mu.lms.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mu.lms.dto.EBookDto;
import uz.mu.lms.model.EBook;

@Mapper(componentModel = "spring", uses = AttachmentMapper.class)
public abstract class EBookMapper implements AbstractMapper<EBook, EBookDto> {

    @Mapping(target = "bookCategoryId", expression = "java(entity.getBookCategory().getId())")
    public abstract EBookDto toDto(EBook entity);
}


