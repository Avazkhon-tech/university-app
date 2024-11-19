package uz.mu.lms.service.mapper;

import org.mapstruct.Mapper;
import uz.mu.lms.dto.UserDto;
import uz.mu.lms.model.User;

@Mapper(componentModel = "spring")
public abstract class UserMapper implements AbstractMapper<User, UserDto> {
}
