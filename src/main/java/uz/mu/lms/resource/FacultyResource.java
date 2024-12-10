package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.FacultyDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.service.faculty.IFacultyService;

import java.util.List;

@RestController
@RequestMapping("/api/faculty")
@RequiredArgsConstructor
public class FacultyResource {

    private final IFacultyService facultyService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<List<FacultyDto>>> getAllFaculties(@PageableDefault Pageable pageable) {
        return facultyService.getAllFaculties(pageable);
    }

    @PostMapping
    public ResponseEntity<FacultyDto> createFaculty(@RequestBody FacultyDto facultyDto) {
        return facultyService.createFaculty(facultyDto);
    }
}
