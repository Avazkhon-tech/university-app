package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.UserDto;
import uz.mu.lms.service.user.UserService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseDto<UserDto> addUser(UserDto userDto) {
        return userService.addUser(userDto);
    }
}
