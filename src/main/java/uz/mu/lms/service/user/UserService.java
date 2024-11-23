package uz.mu.lms.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.PaginatedResponseDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.UserDto;
import uz.mu.lms.exceptions.DuplicateKeyValueException;
import uz.mu.lms.model.User;
import uz.mu.lms.repository.RoleRepository;
import uz.mu.lms.repository.UserRepository;
import uz.mu.lms.service.mapper.UserMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<ResponseDto<UserDto>> addUser(UserDto userDto) {
        User entity = userMapper.toEntity(userDto);
        entity.setRoles(List.of(roleRepository.findByName("ROLE_USER")));

        boolean existsByPhoneNumber = userRepository.existsByPhoneNumber(userDto.getPhoneNumber());
        boolean existsByUsername = userRepository.existsByUsername(userDto.getUsername());
        if (existsByUsername) {
            throw new DuplicateKeyValueException("Username already exists");
        } else if (existsByPhoneNumber) {
            throw new DuplicateKeyValueException("Phone number already exists");
        }

        entity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User saved = userRepository.save(entity);

        ResponseDto<UserDto> responseDto = ResponseDto.<UserDto>builder()
                .success(true)
                .code(200)
                .message("User has been added successfully")
                .data(userMapper.toDto(saved))
                .build();
        return ResponseEntity.ok(responseDto);
    }

    public ResponseEntity<PaginatedResponseDto<List<UserDto>>> getAllUsers(Integer page, Integer size) {
        Pageable pageable =  PageRequest.of(page, size);

        List<UserDto> list  = userRepository.findAll(pageable).stream().map(userMapper::toDto).toList();

        PaginatedResponseDto<List<UserDto>> responseDto = PaginatedResponseDto.<List<UserDto>>builder()
                .success(true)
                .code(200)
                .message("Successfully retrieved")
                .data(list)
                .page(page)
                .size(size)
                .build();
        return ResponseEntity.ok(responseDto);
    }


    public ResponseEntity<ResponseDto<UserDto>> getUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User with id " + id + " not found")
        );

        ResponseDto<UserDto> responseDto = ResponseDto.<UserDto>builder()
                .success(true)
                .code(200)
                .message("Successfully retrieved")
                .data(userMapper.toDto(user))
                .build();
        return ResponseEntity.ok(responseDto);
    }
}
























