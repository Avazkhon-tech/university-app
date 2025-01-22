package uz.mu.lms.service;

import org.springframework.http.ResponseEntity;
import uz.mu.lms.dto.LocationDto;
import uz.mu.lms.dto.ResponseDto;

public interface AttendanceService {

    byte[] generateAttendanceQR(Integer lessonId);

    ResponseDto<?> recordAttendance(String qrUUID, LocationDto locationDto);
}
