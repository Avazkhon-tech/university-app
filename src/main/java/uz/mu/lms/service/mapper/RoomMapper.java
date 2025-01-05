package uz.mu.lms.service.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;
import uz.mu.lms.dto.AttachmentDto;
import uz.mu.lms.dto.RoomDto;
import uz.mu.lms.model.Attachment;
import uz.mu.lms.model.Room;

@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class RoomMapper implements AbstractMapper<Room, RoomDto> {
}


