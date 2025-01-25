package uz.mu.lms.resource;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.UserDto;
import uz.mu.lms.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    // ONLY FOR ADMINS
    @PostMapping
    public ResponseDto<UserDto> addUser(@Valid @RequestBody UserDto userDto) {
        return ResponseDto.success(userService.addUser(userDto));
    }

    @GetMapping
    public PaginatedResponseDto<List<UserDto>> getAllUsers(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        List<UserDto> users = userService.getAllUsers(page, size);
        return PaginatedResponseDto.success(page, users.size(), users);
    }

    @GetMapping("/{id}")
    public ResponseDto<UserDto> getUserById(@PathVariable Integer id) {
        return ResponseDto.success(userService.getUser(id));
    }
}
