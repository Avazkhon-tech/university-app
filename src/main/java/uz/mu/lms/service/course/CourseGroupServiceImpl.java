package uz.mu.lms.service.course;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.CourseGroupDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.exceptions.UserAlreadyExistsException;
import uz.mu.lms.model.Course;
import uz.mu.lms.model.CourseGroup;
import uz.mu.lms.model.Student;
import uz.mu.lms.model.Teacher;
import uz.mu.lms.projection.StudentCourseProjection;
import uz.mu.lms.repository.CourseGroupRepository;
import uz.mu.lms.repository.CourseRepository;
import uz.mu.lms.repository.StudentRepository;
import uz.mu.lms.repository.TeacherRepository;
import uz.mu.lms.service.mapper.CourseGroupMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseGroupServiceImpl implements CourseGroupService {

    @Value("${spring.defaultValues.host}")
    String hostAddr;

    private final CourseGroupRepository courseGroupRepository;
    private final CourseGroupMapper courseGroupMapper;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Override
    public List<StudentCourseProjection> getAllGroups(Integer courseId) {
        return courseGroupRepository.findByCourseId(courseId, hostAddr);
    }

    @Override
    public ResponseDto<CourseGroupDto> createGroup(CourseGroupDto courseGroupDto, Integer courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course with id %s not found"
                        .formatted(courseId)));

        Teacher teacher = teacherRepository.findById(courseGroupDto.teacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher with id %s not found"
                        .formatted(courseGroupDto.teacherId())));

        Teacher teacherAssistant = teacherRepository.findById(courseGroupDto.teacherAssistantId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher Assistant with id %s not found"
                        .formatted(courseGroupDto.teacherAssistantId())));

        CourseGroup entity = courseGroupMapper.toEntity(courseGroupDto);
        entity.setTeacher(teacher);
        entity.setTeacherAssistant(teacherAssistant);
        entity.setCourse(course);
        CourseGroup saved = courseGroupRepository.save(entity);

        CourseGroupDto dto = new CourseGroupDto(
                saved.getName(),
                saved.getTeacher().getId(),
                saved.getTeacherAssistant().getId());

        return ResponseDto.<CourseGroupDto>builder()
                .code(200)
                .data(dto)
                .success(true)
                .message("Course group created successfully")
                .build();
    }

    @Override
    public ResponseDto<?> enrollStudentInGroup(Integer groupId, Integer studentId) {

        CourseGroup courseGroup = courseGroupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Course group with id %s not found"
                        .formatted(groupId)));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id %s not found"
                        .formatted(studentId)));

        boolean studentAlreadyExists = courseGroup.getStudents()
                .stream()
                .anyMatch(m -> m.getId().equals(student.getId()));

        if (studentAlreadyExists) {
            throw new UserAlreadyExistsException("Student with id %s already exists in group %s"
                    .formatted(student.getStudentId(), courseGroup.getName())
            );
        }

        courseGroup.getStudents().add(student);
        courseGroupRepository.save(courseGroup);
        return ResponseDto
                .builder()
                .code(200)
                .success(true)
                .message("Student enrolled successfully")
                .build();
    }
}
