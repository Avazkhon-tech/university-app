package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.LessonTemplateDto;
import uz.mu.lms.dto.GroupDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.exceptions.UserAlreadyExistsException;
import uz.mu.lms.model.*;
import uz.mu.lms.model.enums.LessonType;
import uz.mu.lms.repository.*;
import uz.mu.lms.service.GroupService;
import uz.mu.lms.service.mapper.GroupMapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
    private final RoomRepository roomRepository;
    private final TimeslotRepository timeslotRepository;
    private final LessonTemplateRepository lessonTemplateRepository;
    private final LessonRepository lessonRepository;


    @Override
    public ResponseDto<GroupDto> createGroup(GroupDto groupDto) {

        Group group = groupMapper.toEntity(groupDto);

        Department department = departmentRepository.findById(groupDto.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department with id %s not found"
                        .formatted(groupDto.departmentId())));

        group.setDepartment(department);

        List<Course> courses = new ArrayList<>();
        List<LessonTemplate> lessonTemplates = new ArrayList<>();

        for (LessonTemplateDto lessonTemplateDto : groupDto.lessonTemplates()) {
            Teacher teacher = teacherRepository.findById(lessonTemplateDto.teacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher with id %s not found"
                        .formatted(lessonTemplateDto.teacherId())));

            Course course = courseRepository.findById(lessonTemplateDto.courseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course with id %s not found"
                            .formatted(lessonTemplateDto.courseId())));
            courses.add(course);

            Room room = roomRepository.findById(lessonTemplateDto.roomId())
                    .orElseThrow(() -> new ResourceNotFoundException("Room with id %s not found"
                            .formatted(lessonTemplateDto.roomId())));

            TimeSlot timeSlot = timeslotRepository.findById(lessonTemplateDto.timeSlotId())
                    .orElseThrow(() -> new ResourceNotFoundException("Timeslot with id %s not found"
                            .formatted(lessonTemplateDto.timeSlotId())));

            DayOfWeek dayOfWeek = null;
            LessonType lessonType = null;
            try {
                dayOfWeek = DayOfWeek.valueOf(lessonTemplateDto.dayOfWeek());
            } catch (IllegalArgumentException e) {
                throw new ResourceNotFoundException("Day of week could is invalid");
            }

            try {
                lessonType = LessonType.valueOf(lessonTemplateDto.lessonType());
            } catch (IllegalArgumentException e) {
                throw new ResourceNotFoundException("Lesson type could is invalid");
            }

            LessonTemplate lessonTemplate = LessonTemplate
                    .builder()
                    .teacher(teacher)
                    .course(course)
                    .group(group)
                    .room(room)
                    .lessonType(lessonType)
                    .dayOfWeek(dayOfWeek)
                    .timeSlot(timeSlot)
                    .build();

            lessonTemplates.add(lessonTemplate);
        }

        group.setCourses(courses);
        group.setLessonTemplates(lessonTemplates);
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

    @Override
    @Scheduled(cron = "0 0 0 * * SUN")
    public void generateLesson() {
        List<Group> groups = groupRepository.findAll();

        for (Group group : groups) {
            List<LessonTemplate> lessonTemplates = group.getLessonTemplates();
            for (LessonTemplate lessonTemplate : lessonTemplates) {
                Lesson lesson = Lesson
                        .builder()
                        .lessonDate(getNextWeekDay(lessonTemplate.getDayOfWeek()))
                        .lessonTemplate(lessonTemplate)
                        .build();
                lessonRepository.save(lesson);
            }
        }
    }

    private LocalDate getNextWeekDay(DayOfWeek dayOfWeek) {
        LocalDate today = LocalDate.now();
        int daysUntilNextDay = (dayOfWeek.getValue() - today.getDayOfWeek().getValue() + 7) % 7;
        return today.plusDays(daysUntilNextDay);
    }
}
