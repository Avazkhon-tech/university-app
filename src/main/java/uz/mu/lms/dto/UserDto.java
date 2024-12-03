package uz.mu.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"authorities", "enabled", "accountNonExpired", "accountNonLocked", "credentialsNonExpired"})
public class UserDto {

    private Integer id;

    @NotBlank(message = "username field is mandatory")
    @Email(message = "Username must be a valid email")
    private String username;

    @NotBlank(message = "phoneNumber field is mandatory")
    private String phoneNumber;

    @NotBlank(message = "firstName field is mandatory")
    private String firstName;

    @NotBlank(message = "lastName field is mandatory")
    private String lastName;

    private String password;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    private String gender;

    @Email(message = "Invalid email format")
    private String personal_email;

    private String address;

    private List<RoleDto> authorities;
}
