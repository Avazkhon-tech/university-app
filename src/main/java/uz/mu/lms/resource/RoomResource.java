package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.RoomDto;
import uz.mu.lms.service.RoomService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room")
public class RoomResource {

    private final RoomService roomService;

    @GetMapping
    public ResponseDto<List<RoomDto>> getAllRooms() {
        return ResponseDto.success(roomService.getAllRooms());
    }

    @PostMapping
    public ResponseDto<RoomDto> addRoom(@RequestBody RoomDto roomDto) {
        return ResponseDto.success(roomService.addRoom(roomDto));
    }
}
