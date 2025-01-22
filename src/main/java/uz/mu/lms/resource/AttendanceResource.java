package uz.mu.lms.resource;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.LocationDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.projection.AttendanceSummaryProjection;
import uz.mu.lms.service.AttendanceService;

import java.util.List;

import static uz.mu.lms.dto.ResponseDto.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendance")
public class AttendanceResource {

    private final AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<byte[]> generateQRCode(@RequestParam Integer lessonId) {
        byte[] qrCodeImage = attendanceService.generateAttendanceQR(lessonId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=qr_code.png")
                .contentType(MediaType.IMAGE_PNG)
                .body(qrCodeImage);
    }

    @PostMapping("/{qrId}")
    public ResponseDto<?> recordAttendance(@PathVariable String qrId,
                                           @RequestBody LocationDto locationDto) {
        attendanceService.recordAttendance(qrId, locationDto);
        return ResponseDto.success("Attendance has been recorded");
    }

    @GetMapping("/summary")
    public ResponseDto<List<AttendanceSummaryProjection>> getAttendanceSummary() {
        List<AttendanceSummaryProjection> attendanceSummaries = attendanceService.getAttendanceSummaries();
        return ResponseDto.success(attendanceSummaries);
    }
}