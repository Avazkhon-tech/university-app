package uz.mu.lms.service;

import uz.mu.lms.dto.RoomDto;
import uz.mu.lms.model.Room;

import java.util.List;

public interface RoomService {

    RoomDto addRoom(RoomDto roomDto);
    List<RoomDto> getAllRooms();

    Room findRoomById(Integer id);
}