package uz.mu.lms.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.mu.lms.model.MyUserDetails;
import uz.mu.lms.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new MyUserDetails(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found")));
    }
}
