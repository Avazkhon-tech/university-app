package uz.mu.lms.service;

import uz.mu.lms.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto addUser(UserDto userDto);
    List<UserDto> getAllUsers(Integer page, Integer size);
    UserDto getUser(Integer id);
}
























