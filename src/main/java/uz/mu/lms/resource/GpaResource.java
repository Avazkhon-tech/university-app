package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.FacultyDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.projection.GpaProjection;
import uz.mu.lms.service.FacultyService;
import uz.mu.lms.service.GpaService;

import java.util.List;

@RestController
@RequestMapping("/api/gpa")
@RequiredArgsConstructor
public class GpaResource {

    private final GpaService gpaService;

    @GetMapping
    public ResponseDto<List<GpaProjection>> getAllFaculties() {
        return ResponseDto.success(gpaService.getAllGpa());
    }
}
