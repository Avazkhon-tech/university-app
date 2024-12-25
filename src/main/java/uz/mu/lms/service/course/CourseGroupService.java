package uz.mu.lms.service.course;

import uz.mu.lms.dto.CourseGroupDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.projection.StudentCourseProjection;

import java.util.List;

public interface CourseGroupService {

    ResponseDto<CourseGroupDto> createGroup(CourseGroupDto courseGroupDto, Integer courseId);

    ResponseDto<?> enrollStudentInGroup(Integer groupId, Integer studentId);
}
