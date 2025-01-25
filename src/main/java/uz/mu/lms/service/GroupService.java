package uz.mu.lms.service;

import uz.mu.lms.dto.GroupDto;

public interface GroupService {

    GroupDto createGroup(GroupDto groupDto);

    void enrollStudentInGroup(Integer groupId, Integer studentId);

}
