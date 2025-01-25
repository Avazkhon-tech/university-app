package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.RoomDto;
import uz.mu.lms.exceptions.ResourceAlreadyExistsException;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.model.Room;
import uz.mu.lms.repository.RoomRepository;
import uz.mu.lms.service.RoomService;
import uz.mu.lms.service.mapper.RoomMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public RoomDto addRoom(RoomDto roomDto) {
        boolean exists = roomRepository.existsByRoomNumber(roomDto.roomNumber());

        if (exists) {
            throw new ResourceAlreadyExistsException("Room with number " + roomDto.roomNumber() + " already exists");
        }

        Room saved = roomRepository.save(roomMapper.toEntity(roomDto));

        return roomMapper.toDto(saved);
    }

    @Override
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(roomMapper::toDto)
                .toList();
    }

    @Override
    public Room findRoomById(Integer id) {
        return roomRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Room with id %d not found".formatted(id))
        );
    }
}
