package uz.mu.lms.service;

import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.RoomDto;
import java.util.List;

public interface RoomService {

    ResponseDto<RoomDto> addRoom(RoomDto roomDto);
    ResponseDto<List<RoomDto>> getAllRooms();
}