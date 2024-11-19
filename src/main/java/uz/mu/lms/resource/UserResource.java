package uz.mu.lms.resource;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.UserDto;
import uz.mu.lms.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @PostMapping()
    public ResponseDto<UserDto> addUser(@Valid @RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @PatchMapping("/{id}")
    public ResponseDto<UserDto> partialUpdateUser(@PathVariable Integer id, @RequestBody UserDto userDto) {
        return userService.partialUpdateUser(id, userDto);
    }

    @GetMapping
    public PaginatedResponseDto<List<UserDto>> getAllUsers(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return userService.getAllUsers(page, size);
    }

    @GetMapping("/{id}")
    public ResponseDto<UserDto> getUserById(@PathVariable Integer id) {
        return userService.getUser(id);
    }

}
