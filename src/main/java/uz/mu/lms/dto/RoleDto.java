package uz.mu.lms.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class RoleDto implements GrantedAuthority {

    private Integer id;
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
