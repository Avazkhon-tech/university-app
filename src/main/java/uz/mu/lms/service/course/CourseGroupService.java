package uz.mu.lms.service.course;

import uz.mu.lms.dto.CourseGroupDto;
import uz.mu.lms.model.CourseGroup;
import uz.mu.lms.projection.CourseGroupProjection;

import java.util.List;

public interface CourseGroupService {

    List<CourseGroupProjection> getAllGroups(Integer courseId);

    boolean createGroup(CourseGroupDto courseGroupDto, Integer courseId);

 }
