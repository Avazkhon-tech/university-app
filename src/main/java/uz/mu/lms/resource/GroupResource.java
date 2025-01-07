package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.GroupDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.service.GroupService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupResource {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<ResponseDto<GroupDto>> createGroup(@RequestBody GroupDto groupDto) {
        return ResponseEntity.ok(groupService.createGroup(groupDto));
    }

    @PostMapping("/student")
    public ResponseEntity<ResponseDto<?>> enrollStudentInGroup(
            @RequestParam Integer groupId,
            @RequestParam Integer studentId
    ) {
        return ResponseEntity.ok(groupService.enrollStudentInGroup(groupId, studentId));
    }
}
