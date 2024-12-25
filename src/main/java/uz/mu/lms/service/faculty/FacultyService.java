package uz.mu.lms.service.faculty;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.FacultyDto;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.model.Faculty;
import uz.mu.lms.repository.FacultyRepository;
import uz.mu.lms.repository.TeacherRepository;
import uz.mu.lms.service.mapper.FacultyMapper;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FacultyService implements IFacultyService {

    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;
    private final TeacherRepository teacherRepository;

    @Override
    public ResponseEntity<PaginatedResponseDto<List<FacultyDto>>> getAllFaculties(Pageable pageable) {

        var list = facultyRepository.findAll(pageable)
                .stream()
                .map(facultyMapper::toDto)
                .toList();

        var response = PaginatedResponseDto
                .<List<FacultyDto>>builder()
                .success(true)
                .message("Successfully retrieved faculties")
                .code(200)
                .data(list)
                .size(list.size())
                .page(pageable.getPageNumber() + 1)
                .build();


        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<FacultyDto> createFaculty(FacultyDto facultyDto) {

        var faculty = facultyMapper.toEntity(facultyDto);
        Faculty saved = facultyRepository.save(faculty);
        return ResponseEntity.ok(facultyMapper.toDto(saved));
    }
}
