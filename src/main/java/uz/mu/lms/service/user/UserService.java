package uz.mu.lms.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.UserDto;
import uz.mu.lms.exceptions.DuplicateKeyValue;
import uz.mu.lms.model.User;
import uz.mu.lms.repository.RoleRepository;
import uz.mu.lms.repository.UserRepository;
import uz.mu.lms.service.mapper.UserMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public ResponseDto<UserDto> addUser(UserDto userDto) {
        User entity = userMapper.toEntity(userDto);
        entity.setRoles(List.of(roleRepository.findByName("ROLE_USER")));

        boolean existsByPhoneNumber = userRepository.existsByPhoneNumber(userDto.getPhoneNumber());
        boolean existsByUsername = userRepository.existsByUsername(userDto.getUsername());
        if (existsByUsername) {
            throw new DuplicateKeyValue("Username already exists");
        } else if (existsByPhoneNumber) {
            throw new DuplicateKeyValue("Phone number already exists");
        }

        User saved = userRepository.save(entity);

        return ResponseDto.<UserDto>builder()
                .success(true)
                .code(200)
                .message("User has been added successfully")
                .data(userMapper.toDto(saved))
                .build();
    }

    public ResponseDto<UserDto> partialUpdateUser(Integer id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User with id " + id + " not found"));

        if(userDto.getUsername() != null) {
            user.setUsername(userDto.getUsername());
        }

        if(userDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }

        if(userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }

        if (userDto.getAddress() != null) {
            user.setAddress(userDto.getAddress());
        }

        if (userDto.getGender() != null) {
            user.setGender(userDto.getGender());
        }

        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }

        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }

        if (userDto.getBirthDate() != null) {
            user.setBirthDate(user.getBirthDate());
        }

        if (userDto.getPersonal_email() != null) {
            user.setPersonal_email(user.getPersonal_email());
        }


        System.out.println(user);
        UserDto dto = userMapper.toDto(userRepository.save(user));

        return ResponseDto.<UserDto>builder()
                .code(200)
                .success(true)
                .message("Updated successfully")
                .data(dto)
                .build();
    }
}

























