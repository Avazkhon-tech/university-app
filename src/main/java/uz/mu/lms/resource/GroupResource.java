package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.GroupDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.service.GroupService;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class GroupResource {

    private final GroupService groupService;

    @PostMapping
    public ResponseDto<GroupDto> createGroup(@RequestBody GroupDto groupDto) {
        return ResponseDto.success(groupService.createGroup(groupDto));
    }

    @PostMapping("/student")
    public ResponseDto<?> enrollStudentInGroup(
            @RequestParam Integer groupId,
            @RequestParam Integer studentId) {
        groupService.enrollStudentInGroup(groupId, studentId);
        return ResponseDto.success("OK");
    }
}
