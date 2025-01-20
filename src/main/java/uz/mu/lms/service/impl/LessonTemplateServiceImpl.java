package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mu.lms.dto.LectureTemplateDto;
import uz.mu.lms.dto.LessonTemplateDto;
import uz.mu.lms.exceptions.ResourceNotFoundException;
import uz.mu.lms.model.*;
import uz.mu.lms.model.enums.LessonType;
import uz.mu.lms.repository.*;
import uz.mu.lms.service.LessonTemplateService;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonTemplateServiceImpl implements LessonTemplateService {

    private final GroupRepository groupRepository;
    private final RoomRepository roomRepository;
    private final CourseRepository courseRepository;
    private final TimeslotRepository timeslotRepository;
    private final TeacherRepository teacherRepository;
    private final LessonTemplateRepository lessonTemplateRepository;

    @Override
    public void createTemplate(LessonTemplateDto lessonTemplateDto, Integer groupId) {
        Group group = fetchGroup(groupId);
        LessonTemplate lessonTemplate = buildLessonTemplate(
                lessonTemplateDto.teacherId(),
                lessonTemplateDto.courseId(),
                lessonTemplateDto.roomId(),
                lessonTemplateDto.timeSlotId(),
                lessonTemplateDto.dayOfWeek(),
                lessonTemplateDto.lessonType()
        );

        lessonTemplate = lessonTemplateRepository.save(lessonTemplate);
        group.getLessonTemplates().add(lessonTemplate);
        groupRepository.save(group);
    }

    @Override
    @Transactional
    public void createTemplate(LectureTemplateDto lectureTemplateDto) {
        LessonTemplate lessonTemplate = buildLessonTemplate(
                lectureTemplateDto.teacherId(),
                lectureTemplateDto.courseId(),
                lectureTemplateDto.roomId(),
                lectureTemplateDto.timeSlotId(),
                lectureTemplateDto.dayOfWeek(),
                lectureTemplateDto.lessonType()
        );

        List<Group> groups = lectureTemplateDto.groupIds().stream()
                .map(this::fetchGroup)
                .collect(Collectors.toList());

        LessonTemplate saved = lessonTemplateRepository.save(lessonTemplate);

        groups.forEach(group -> {
            group.getLessonTemplates().add(saved);
        });

        groupRepository.saveAll(groups);
    }

    private Group fetchGroup(Integer groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Group with id %s not found", groupId)));
    }

    private Teacher fetchTeacher(Integer teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Teacher with id %s not found", teacherId)));
    }

    private Course fetchCourse(Integer courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Course with id %s not found", courseId)));
    }

    private Room fetchRoom(Integer roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Room with id %s not found", roomId)));
    }

    private TimeSlot fetchTimeSlot(Integer timeSlotId) {
        return timeslotRepository.findById(timeSlotId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Timeslot with id %s not found", timeSlotId)));
    }

    private LessonTemplate buildLessonTemplate(
            Integer teacherId, Integer courseId, Integer roomId,
            Integer timeSlotId, String dayOfWeek, String lessonType
    ) {
        Teacher teacher = fetchTeacher(teacherId);
        Course course = fetchCourse(courseId);
        Room room = fetchRoom(roomId);
        TimeSlot timeSlot = fetchTimeSlot(timeSlotId);

        DayOfWeek day = parseEnum(DayOfWeek.class, dayOfWeek, "Day of week is invalid");
        LessonType type = parseEnum(LessonType.class, lessonType, "Lesson type is invalid");

        return LessonTemplate.builder()
                .teacher(teacher)
                .course(course)
                .room(room)
                .timeSlot(timeSlot)
                .dayOfWeek(day)
                .lessonType(type)
                .build();
    }

    private <E extends Enum<E>> E parseEnum(Class<E> enumType, String value, String errorMessage) {
        try {
            return Enum.valueOf(enumType, value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(errorMessage);
        }
    }
}
