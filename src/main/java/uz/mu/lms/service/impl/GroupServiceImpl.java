package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.GroupDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.exceptions.ResourceAlreadyExistsException;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.exceptions.UserAlreadyExistsException;
import uz.mu.lms.model.*;
import uz.mu.lms.repository.*;
import uz.mu.lms.service.GroupService;
import uz.mu.lms.service.mapper.GroupMapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final GroupMapper groupMapper;
    private final RoomRepository roomRepository;
    private final TimeslotRepository timeslotRepository;
    private final LessonRepository lessonRepository;
    private final AcademicCalendarRepository academicCalendarRepository;


    @Override
    public ResponseDto<?> createGroup(GroupDto groupDto) {

        Group group = groupMapper.toEntity(groupDto);

        Department department = departmentRepository.findById(groupDto.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department with id %s not found"
                        .formatted(groupDto.departmentId())));

        group.setDepartment(department);

        List<Course> courses = groupDto.courseIds()
                .stream()
                .map(courseId -> courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course with id %s not found".formatted(courseId)))).collect(Collectors.toList());

        group.setCourses(courses);
        Group saved = groupRepository.save(group);

        return ResponseDto.builder()
                .code(200)
                .success(true)
                .message("Group with name %s and id %d has been created successfully".formatted(saved.getName(), saved.getId()))
                .build();
    }

    @Override
    public ResponseDto<?> enrollStudentInGroup(Integer groupId, Integer studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id %s not found"
                        .formatted(studentId)));

        if (student.getGroup() != null) {
            throw new ResourceAlreadyExistsException("Student with id %s has already been assigned to a group"
                    .formatted(student.getId())
            );
        }

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group with id %s not found"
                        .formatted(groupId)));

        boolean studentAlreadyExists = group.getStudents()
                .stream()
                .anyMatch(m -> m.getId().equals(student.getId()));

        if (studentAlreadyExists) {
            throw new UserAlreadyExistsException("Student with id %s already exists in group %s"
                    .formatted(student.getStudentId(), group.getName()));
        }

        group.getStudents().add(student);
        student.setGroup(group);
        studentRepository.save(student);
        groupRepository.save(group);
        return ResponseDto
                .builder()
                .code(200)
                .success(true)
                .message("Student enrolled successfully")
                .build();
    }
}
