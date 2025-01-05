package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.BorrowingDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.RoomDto;
import uz.mu.lms.exceptions.ResourceAlreadyExistsException;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.exceptions.UserNotFoundException;
import uz.mu.lms.model.Borrowing;
import uz.mu.lms.model.PhysicalBook;
import uz.mu.lms.model.Room;
import uz.mu.lms.model.User;
import uz.mu.lms.repository.BorrowingRepository;
import uz.mu.lms.repository.PhysicalBookRepository;
import uz.mu.lms.repository.RoomRepository;
import uz.mu.lms.repository.UserRepository;
import uz.mu.lms.service.BorrowingService;
import uz.mu.lms.service.RoomService;
import uz.mu.lms.service.mapper.BorrowingMapper;
import uz.mu.lms.service.mapper.RoomMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public ResponseDto<RoomDto> addRoom(RoomDto roomDto) {
        boolean exists = roomRepository.existsByRoomNumber(roomDto.roomNumber());

        if (exists) {
            throw new ResourceAlreadyExistsException("Room with number " + roomDto.roomNumber() + " already exists");
        }

        Room saved = roomRepository.save(roomMapper.toEntity(roomDto));

        return ResponseDto
                .<RoomDto>builder()
                .code(200)
                .success(true)
                .message("OK")
                .data(roomMapper.toDto(saved))
                .build();
    }

    @Override
    public ResponseDto<List<RoomDto>> getAllRooms() {

        List<RoomDto> list = roomRepository.findAll()
                .stream()
                .map(roomMapper::toDto)
                .toList();

        return ResponseDto.<List<RoomDto>>
                builder()
                .code(200)
                .success(true)
                .message("OK")
                .data(list)
                .build();
    }
}
