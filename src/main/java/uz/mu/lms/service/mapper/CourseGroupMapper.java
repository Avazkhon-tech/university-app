package uz.mu.lms.service.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import uz.mu.lms.dto.CourseDto;
import uz.mu.lms.dto.CourseGroupDto;
import uz.mu.lms.model.Course;
import uz.mu.lms.model.CourseGroup;

@Mapper(componentModel = "spring", uses = {CourseMapper.class, TeacherMapper.class})
@RequiredArgsConstructor
public abstract class CourseGroupMapper implements AbstractMapper<CourseGroup, CourseGroupDto> {

}


