package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.FacultyDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/api/faculty")
@RequiredArgsConstructor
public class FacultyResource {

    private final FacultyService facultyService;

    @GetMapping
    public PaginatedResponseDto<List<FacultyDto>> getAllFaculties(@PageableDefault Pageable pageable) {
        List<FacultyDto> faculties = facultyService.getAllFaculties(pageable);
        return PaginatedResponseDto.success(pageable.getPageNumber(), faculties.size(), faculties);
    }

    @PostMapping
    public ResponseDto<FacultyDto> createFaculty(@RequestBody FacultyDto facultyDto) {
        FacultyDto faculty = facultyService.createFaculty(facultyDto);
        return ResponseDto.success(faculty);
    }
}
