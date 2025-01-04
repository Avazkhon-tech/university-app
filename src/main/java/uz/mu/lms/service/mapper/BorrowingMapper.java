package uz.mu.lms.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mu.lms.dto.BorrowingDto;
import uz.mu.lms.dto.EBookDto;
import uz.mu.lms.model.Borrowing;
import uz.mu.lms.model.EBook;

@Mapper(componentModel = "spring", uses = PhysicalBookMapper.class)
public abstract class BorrowingMapper implements AbstractMapper<Borrowing, BorrowingDto> {

    @Mapping(target = "username", expression = "java(borrowing.getUser().getUsername())")
    @Mapping(target = "bookId", expression = "java(borrowing.getBook().getId())")
    public abstract BorrowingDto toDto(Borrowing borrowing);
}


