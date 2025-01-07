package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.CourseTeacherDto;
import uz.mu.lms.dto.GroupDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.exceptions.UserAlreadyExistsException;
import uz.mu.lms.model.*;
import uz.mu.lms.repository.*;
import uz.mu.lms.service.GroupService;
import uz.mu.lms.service.mapper.GroupMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final GroupMapper groupMapper;


    @Override
    public ResponseDto<GroupDto> createGroup(GroupDto groupDto) {

        Group group = groupMapper.toEntity(groupDto);

        Department department = departmentRepository.findById(groupDto.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department with id %s not found"
                        .formatted(groupDto.departmentId())));

        group.setDepartment(department);

        List<Course> courses = new ArrayList<>();
        List<CourseTeacher> courseTeachers = new ArrayList<>();

        for (CourseTeacherDto courseTeacherDto: groupDto.coursesAndTeachers()) {
            Teacher teacher = teacherRepository.findById(courseTeacherDto.teacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher with id %s not found"
                        .formatted(courseTeacherDto.teacherId())));

            Course course = courseRepository.findById(courseTeacherDto.courseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course with id %s not found"
                            .formatted(courseTeacherDto.courseId())));
            courses.add(course);

            CourseTeacher courseTeacher = CourseTeacher
                    .builder()
                    .teacher(teacher)
                    .course(course)
                    .group(group)
                    .build();

            courseTeachers.add(courseTeacher);
        }

        group.setCourses(courses);
        group.setCourseTeachers(courseTeachers);
        Group groupSaved = groupRepository.save(group);

        return ResponseDto.<GroupDto>builder()
                .code(200)
                .data(groupMapper.toDto(groupSaved))
                .success(true)
                .message("Course group created successfully")
                .build();
    }

    @Override
    public ResponseDto<?> enrollStudentInGroup(Integer groupId, Integer studentId) {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group with id %s not found"
                        .formatted(groupId)));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id %s not found"
                        .formatted(studentId)));

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
