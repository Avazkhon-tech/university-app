package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.AcademicCalendarDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.service.AcademicCalendarService;
import uz.mu.lms.service.AttendanceService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/academic-calendar")
public class AcademicCalendarResource {

    private final AcademicCalendarService academicCalendarService;

    @PostMapping
    public ResponseEntity<ResponseDto<AcademicCalendarDto>> createAcademicCalendar(@RequestBody AcademicCalendarDto academicCalendarDto) {
        ResponseDto<AcademicCalendarDto> academicCalendar = academicCalendarService.createAcademicCalendar(academicCalendarDto);
        return ResponseEntity.ok(academicCalendar);
    }
}