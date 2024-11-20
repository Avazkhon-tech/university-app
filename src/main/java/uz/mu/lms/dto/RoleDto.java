package uz.mu.lms.dto;

import org.springframework.security.core.GrantedAuthority;

public record RoleDto(Integer id, String name) implements GrantedAuthority {

    @Override
    public String getAuthority() {
        return name;
    }
}
