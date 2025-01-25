package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.UserDto;
import uz.mu.lms.exceptions.DuplicateKeyValueException;
import uz.mu.lms.model.User;
import uz.mu.lms.model.enums.RoleName;
import uz.mu.lms.repository.RoleRepository;
import uz.mu.lms.repository.UserRepository;
import uz.mu.lms.service.UserService;
import uz.mu.lms.service.mapper.UserMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto addUser(UserDto userDto) {
        User entity = userMapper.toEntity(userDto);
        entity.setRole(roleRepository.findByName(RoleName.ROLE_USER.toString()));

        boolean existsByPhoneNumber = userRepository.existsByPhoneNumber(userDto.getPhoneNumber());
        boolean existsByUsername = userRepository.existsByUsername(userDto.getUsername());
        if (existsByUsername) {
            throw new DuplicateKeyValueException("Username already exists");
        } else if (existsByPhoneNumber) {
            throw new DuplicateKeyValueException("Phone number already exists");
        }

        entity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User saved = userRepository.save(entity);
        return userMapper.toDto(saved);
    }

    @Override
    public List<UserDto> getAllUsers(Integer page, Integer size) {
        Pageable pageable =  PageRequest.of(page, size);

        return userRepository.findAll(pageable).stream()
                .map(userMapper::toDto)
                .toList();

    }


    @Override
    public UserDto getUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User with id " + id + " not found")
        );
        return userMapper.toDto(user);
    }
}
























