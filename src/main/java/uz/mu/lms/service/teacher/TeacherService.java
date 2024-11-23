package uz.mu.lms.service.teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.TeacherDto;
import uz.mu.lms.exceptions.UserAlreadyExistsException;
import uz.mu.lms.exceptions.UserNotFoundException;
import uz.mu.lms.model.Teacher;
import uz.mu.lms.repository.TeacherRepository;
import uz.mu.lms.service.mapper.TeacherMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService implements ITeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;


    @Override
    public ResponseEntity<ResponseDto<TeacherDto>> addTeacher(TeacherDto teacherDto) {
        if (teacherRepository.existsByUser_PhoneNumber(teacherDto.userDto().getPhoneNumber()))
            throw new UserAlreadyExistsException("Teacher with phone number " +
                    teacherDto.userDto().getPhoneNumber() + " already exists");

        if (teacherRepository.existsByUser_Username(teacherDto.userDto().getUsername()))
            throw new UserAlreadyExistsException("Teacher with username " +
                    teacherDto.userDto().getUsername() + " already exists");

        if (teacherRepository.existsByTeacherId(teacherDto.teacherId()))
            throw new UserAlreadyExistsException("Teacher with teacher Id " +
                    teacherDto.teacherId() + " already exists");

        Teacher entity = teacherMapper.toEntity(teacherDto);
        entity.getUser().setPassword(teacherDto.userDto().getPassword());
        Teacher saved = teacherRepository.save(entity);
        ResponseDto<TeacherDto> teacherDtoResponseDto = ResponseDto
                .<TeacherDto>builder()
                .success(true)
                .code(200)
                .message("Teacher added successfully")
                .data(teacherMapper.toDto(saved))
                .build();

        return ResponseEntity.ok(teacherDtoResponseDto);
     }

    @Override
    public ResponseEntity<ResponseDto<?>> deleteTeacher(Integer id) {
        if (!teacherRepository.existsById(id))
            throw new UserNotFoundException("Teacher with id " + id + " does not exist");

        teacherRepository.deleteById(id);
        return ResponseEntity.ok(ResponseDto
                .builder()
                .success(true)
                .code(200)
                .message("Teacher with id " + id + " deleted successfully")
                .build()
        );
    }

    @Override
    public ResponseEntity<PaginatedResponseDto<List<TeacherDto>>> getTeachers(Pageable pageable) {
        Page<Teacher> all = teacherRepository.findAll(pageable);
        List<TeacherDto> list = all.stream().map(teacherMapper::toDto).toList();
        PaginatedResponseDto<List<TeacherDto>> dtoPaginatedResponseDto = PaginatedResponseDto
                .<List<TeacherDto>>builder()
                .success(true)
                .code(200)
                .message("Teacher list retrieved successfully")
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .data(list)
                .build();
        return ResponseEntity.ok(dtoPaginatedResponseDto);
    }

}
