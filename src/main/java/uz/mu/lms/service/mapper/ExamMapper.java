package uz.mu.lms.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.mu.lms.dto.ExamCreateDto;
import uz.mu.lms.model.Exam;
import uz.mu.lms.service.CourseService;
import uz.mu.lms.service.RoomService;
import uz.mu.lms.service.StudentService;
import uz.mu.lms.service.TeacherService;

@Mapper(componentModel = "spring")
public abstract class ExamMapper implements AbstractMapper<Exam, ExamCreateDto> {

    @Autowired
    protected CourseService courseService;
    @Autowired
    protected RoomService roomService;
    @Autowired
    protected TeacherService teacherService;
    @Autowired
    protected StudentService studentService;

    @Mapping(target = "course", expression = "java(courseService.findCourseById(dto.courseId()))")
    @Mapping(target = "room", expression = "java(roomService.findRoomById(dto.roomId()))")
    @Mapping(target = "teachers", expression = "java(teacherService.findAllTeachersByIds(dto.teacherIds()))")
    @Mapping(target = "students", expression = "java(studentService.findStudentsByGroupIds(dto.groupIds()))")
    @Mapping(target = "id", ignore = true)
    public abstract Exam toEntity(ExamCreateDto dto);

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "teacherIds", expression = "java(exam.getTeachers().stream().map(t -> t.getId()).toList())")
    public abstract ExamCreateDto toDto(Exam exam);
}
