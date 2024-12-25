package uz.mu.lms.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.mu.lms.model.Role;
import uz.mu.lms.model.User;

import java.util.Collection;
import java.util.List;

@Getter
public class MyUserDetails implements UserDetails {

    private final Integer id;
    private final String username;
    private final String password;
    private final Role role;

    public MyUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }
}

