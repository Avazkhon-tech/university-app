package uz.mu.lms.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.mu.lms.model.User;
import uz.mu.lms.model.enums.RoleName;
import uz.mu.lms.repository.RoleRepository;
import uz.mu.lms.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        User avaz = User.builder()
                .id(1)
                .username("avazxonnazirov334@gmail.com")
                .password(passwordEncoder.encode("1234"))
                .phoneNumber("+998999928775")
                .firstName("avazxon")
                .lastName("nazirov")
                .role(roleRepository.findByName(RoleName.ROLE_ADMIN.toString()))
                .build();

        try {
            userRepository.save(avaz);
        } catch (Exception e) {}
    }
}
