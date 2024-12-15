package uz.mu.lms.service.course;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import uz.mu.lms.dto.CourseDto;
import uz.mu.lms.model.Course;
import uz.mu.lms.service.mapper.AbstractMapper;
import uz.mu.lms.service.mapper.DepartmentMapper;
import uz.mu.lms.service.mapper.TeacherMapper;

@Mapper(componentModel = "spring", uses = {DepartmentMapper.class, TeacherMapper.class})
@RequiredArgsConstructor
public abstract class CourseMapper implements AbstractMapper<Course, CourseDto> {

}


