package uz.mu.lms.service;

import uz.mu.lms.dto.GroupDto;
import uz.mu.lms.dto.ResponseDto;

public interface GroupService {

    ResponseDto<GroupDto> createGroup(GroupDto groupDto);

    ResponseDto<?> enrollStudentInGroup(Integer groupId, Integer studentId);

    void generateLesson();
}
