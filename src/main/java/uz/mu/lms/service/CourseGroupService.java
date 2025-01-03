package uz.mu.lms.service;

import uz.mu.lms.dto.CourseGroupDto;
import uz.mu.lms.dto.ResponseDto;

public interface CourseGroupService {

    ResponseDto<CourseGroupDto> createGroup(CourseGroupDto courseGroupDto, Integer courseId);

    ResponseDto<?> enrollStudentInGroup(Integer groupId, Integer studentId);
}
