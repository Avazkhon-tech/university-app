package uz.mu.lms.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mu.lms.dto.UserDto;
import uz.mu.lms.model.User;

@Mapper(componentModel = "spring")
public abstract class UserMapper implements AbstractMapper<User, UserDto> {

    @Mapping(target = "password", ignore = true)
    public abstract UserDto toDto(User user);
}
