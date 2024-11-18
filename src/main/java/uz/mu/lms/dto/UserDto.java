package uz.mu.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.mu.lms.model.User;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements UserDetails {

    private Integer id;

    private String username;
    private String password;

    private String firstName;
    private String lastName;

    // TODO add authorities later
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }


    public UserDto(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
