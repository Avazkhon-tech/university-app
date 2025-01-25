package uz.mu.lms.service;

import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.TimeslotDto;

import java.util.List;

public interface TimeslotService {

    TimeslotDto createTimeslot(TimeslotDto timeslotDto);

    ResponseDto<List<TimeslotDto>> getAllTimeslots();
}
