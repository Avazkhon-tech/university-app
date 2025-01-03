package uz.mu.lms.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import uz.mu.lms.dto.FacultyDto;
import uz.mu.lms.dto.PaginatedResponseDto;

import java.util.List;

public interface FacultyService {
    ResponseEntity<PaginatedResponseDto<List<FacultyDto>>> getAllFaculties(Pageable pageable);

    ResponseEntity<FacultyDto> createFaculty(FacultyDto facultyDto);
}
