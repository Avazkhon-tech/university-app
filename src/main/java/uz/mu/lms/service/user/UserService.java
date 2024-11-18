package uz.mu.lms.service.user;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.UserDto;
import uz.mu.lms.model.User;
import uz.mu.lms.repository.UserRepository;
import uz.mu.lms.service.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public ResponseDto<UserDto> addUser(UserDto userDto) {
        User entity = userMapper.toEntity(userDto);
        User saved = userRepository.save(entity);
        return ResponseDto.<UserDto>builder()
                .success(true)
                .code(200)
                .message("User has been added successfully")
                .data(userMapper.toDto(saved))
                .build();
    }
}
