package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.TimeslotDto;
import uz.mu.lms.service.TimeslotService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/timeslot")
public class TimeslotResource {

    private final TimeslotService timeslotService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<TimeslotDto>>> getAllTimeslots() {
        return ResponseEntity.ok(timeslotService.getAllTimeslots());
    }

    @PostMapping
    public ResponseDto<TimeslotDto> createTimeslot(@RequestBody TimeslotDto timeslotDto) {
        return ResponseDto.success(timeslotService.createTimeslot(timeslotDto));
    }
}
