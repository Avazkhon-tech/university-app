package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.TimeslotDto;
import uz.mu.lms.exceptions.ResourceAlreadyExistsException;
import uz.mu.lms.model.TimeSlot;
import uz.mu.lms.repository.TimeslotRepository;
import uz.mu.lms.service.TimeslotService;
import uz.mu.lms.service.mapper.TimeslotMapper;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeslotServiceImpl implements TimeslotService {

    private final TimeslotRepository timeslotRepository;
    private final TimeslotMapper timeslotMapper;

    @Override
    public TimeslotDto createTimeslot(TimeslotDto timeslotDto) {

        boolean exists = timeslotRepository.existsByOrderNumber(timeslotDto.orderNumber());
        if (exists) {
            throw new ResourceAlreadyExistsException("TimeslotService with order number %d already exists"
                    .formatted(timeslotDto.orderNumber()));
        }

        TimeSlot entity = timeslotMapper.toEntity(timeslotDto);
        TimeSlot save = timeslotRepository.save(entity);
        return timeslotMapper.toDto(save);
    }

    @Override
    public ResponseDto<List<TimeslotDto>> getAllTimeslots() {
        List<TimeslotDto> list = timeslotRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(TimeSlot::getOrderNumber))
                .map(timeslotMapper::toDto)
                .toList();

        return ResponseDto.<List<TimeslotDto>>builder()
                .success(true)
                .code(200)
                .message("Retrieved timeslots")
                .data(list)
                .build();
    }
}
