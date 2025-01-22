package uz.mu.lms.service;

import org.springframework.http.ResponseEntity;
import uz.mu.lms.dto.LocationDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.model.Attendance;
import uz.mu.lms.projection.AttendanceSummaryProjection;

import java.util.List;

public interface AttendanceService {

    byte[] generateAttendanceQR(Integer lessonId);

    ResponseDto<?> recordAttendance(String qrUUID, LocationDto locationDto);

    List<AttendanceSummaryProjection> getAttendanceSummaries();
}
