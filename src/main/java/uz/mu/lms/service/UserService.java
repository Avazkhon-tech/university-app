package uz.mu.lms.service;

import org.springframework.http.ResponseEntity;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.UserDto;

import java.util.List;

public interface UserService {

    ResponseEntity<ResponseDto<UserDto>> addUser(UserDto userDto);
    ResponseEntity<PaginatedResponseDto<List<UserDto>>> getAllUsers(Integer page, Integer size);
    ResponseEntity<ResponseDto<UserDto>> getUser(Integer id);
}
























