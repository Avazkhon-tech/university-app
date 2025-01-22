package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.LocationDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.service.AttendanceService;

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
    public ResponseEntity<ResponseDto<?>> recordAttendance(@PathVariable String qrId,
                                                           @RequestBody LocationDto locationDto) {
        ResponseDto<?> responseDto = attendanceService.recordAttendance(qrId, locationDto);
        return ResponseEntity.ok(responseDto);
    }
}