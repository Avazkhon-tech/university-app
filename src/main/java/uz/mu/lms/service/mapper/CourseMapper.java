package uz.mu.lms.service.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import uz.mu.lms.dto.CourseDto;
import uz.mu.lms.dto.DepartmentDto;
import uz.mu.lms.model.Course;
import uz.mu.lms.model.Department;

@Mapper(componentModel = "spring", uses = {DepartmentMapper.class, TeacherMapper.class})
@RequiredArgsConstructor
public abstract class CourseMapper implements AbstractMapper<Course, CourseDto> {

}


