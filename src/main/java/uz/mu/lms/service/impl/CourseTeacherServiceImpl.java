package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.CourseTeacherDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.model.CourseTeacher;
import uz.mu.lms.repository.CourseTeacherRepository;
import uz.mu.lms.repository.CourseRepository;
import uz.mu.lms.repository.GroupRepository;
import uz.mu.lms.repository.TeacherRepository;
import uz.mu.lms.service.CourseTeacherService;
import uz.mu.lms.service.mapper.CourseTeacherMapper;


@Service
@RequiredArgsConstructor
public class CourseTeacherServiceImpl implements CourseTeacherService {

    private final CourseTeacherRepository courseTeacherRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;
    private final CourseTeacherMapper courseInstructorMapper;

    @Override
    public ResponseDto<CourseTeacher> addCourseInstructor(CourseTeacherDto courseTeacherDto) {

//        Teacher teacher = teacherRepository.findById(courseInstructorDto.teacherId())
//                .orElseThrow(() -> new ResourceNotFoundException("Teacher with id %s not found"
//                        .formatted(courseInstructorDto.teacherId())));
//
//        Course course = courseRepository.findById(courseInstructorDto.courseId())
//                .orElseThrow(() -> new ResourceNotFoundException("Course with id %s not found"
//                        .formatted(courseInstructorDto.courseId())));
//
//        Group group = groupRepository.findById(courseInstructorDto.groupId())
//                .orElseThrow(() -> new ResourceNotFoundException("Group with id %s not found"
//                        .formatted(courseInstructorDto.groupId())));
//
//        CourseTeacher courseTeacher = CourseTeacher.builder()
//                .teacher(teacher)
//                .course(course)
//                .group(group)
//                .build();
//
//        CourseTeacher savedCourseTeacher = courseInstructorRepository.save(courseTeacher);
//
//        courseInstructorMapper.toDto(savedCourseTeacher);

        return null;
    }
}
