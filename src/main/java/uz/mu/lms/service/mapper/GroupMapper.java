package uz.mu.lms.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mu.lms.dto.GroupDto;
import uz.mu.lms.model.Group;

@Mapper(componentModel = "spring", uses = {CourseMapper.class, TeacherMapper.class, CourseTeacherMapper.class})
public abstract class GroupMapper implements AbstractMapper<Group, GroupDto> {

    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "department", ignore = true)
    public abstract Group toEntity(GroupDto groupDto);

    @Mapping(target = "coursesAndTeachers", source = "courseTeachers")
    @Mapping(target = "departmentId", expression = "java(group.getDepartment().getId())")
    public abstract GroupDto toDto(Group group);
}


