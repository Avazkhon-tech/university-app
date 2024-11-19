package uz.mu.lms.resource;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.UserDto;
import uz.mu.lms.service.user.UserService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseDto<UserDto> addUser(@Valid @RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @PatchMapping("/user/{id}")
    public ResponseDto<UserDto> partialUpdateUser(@PathVariable Integer id, @RequestBody UserDto userDto) {
        return userService.partialUpdateUser(id, userDto);
    }
}
