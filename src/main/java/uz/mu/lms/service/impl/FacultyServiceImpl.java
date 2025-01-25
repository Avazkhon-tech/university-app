package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.FacultyDto;
import uz.mu.lms.model.Faculty;
import uz.mu.lms.repository.FacultyRepository;
import uz.mu.lms.service.FacultyService;
import uz.mu.lms.service.mapper.FacultyMapper;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;

    @Override
    public List<FacultyDto> getAllFaculties(Pageable pageable) {

        return facultyRepository.findAll(pageable)
                .stream()
                .map(facultyMapper::toDto)
                .toList();
    }

    @Override
    public FacultyDto createFaculty(FacultyDto facultyDto) {

        var faculty = facultyMapper.toEntity(facultyDto);
        Faculty saved = facultyRepository.save(faculty);
        return facultyMapper.toDto(saved);
    }
}
