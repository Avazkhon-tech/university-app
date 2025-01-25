package uz.mu.lms.service;

import org.springframework.data.domain.Pageable;
import uz.mu.lms.dto.FacultyDto;

import java.util.List;

public interface FacultyService {
    List<FacultyDto> getAllFaculties(Pageable pageable);

    FacultyDto createFaculty(FacultyDto facultyDto);
}
