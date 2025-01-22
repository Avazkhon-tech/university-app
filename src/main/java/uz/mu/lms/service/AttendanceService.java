package uz.mu.lms.service;

import uz.mu.lms.dto.LocationDto;
import uz.mu.lms.projection.AttendanceSummaryProjection;

import java.util.List;

public interface AttendanceService {

    byte[] generateAttendanceQR(Integer lessonId);

    void recordAttendance(String qrUUID, LocationDto locationDto);

    List<AttendanceSummaryProjection> getAttendanceSummaries();
}
