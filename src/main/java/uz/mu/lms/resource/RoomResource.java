package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.RoomDto;
import uz.mu.lms.model.Room;
import uz.mu.lms.service.ContentService;
import uz.mu.lms.service.RoomService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room")
public class RoomResource {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<RoomDto>>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @PostMapping
    public ResponseEntity<ResponseDto<RoomDto>> addRoom(@RequestBody RoomDto roomDto) {
        return ResponseEntity.ok(roomService.addRoom(roomDto));
    }
}
