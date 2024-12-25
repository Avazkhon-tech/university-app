package uz.mu.lms.config;


import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.mu.lms.dto.MyUserDetails;

import java.util.Optional;


public class AuditorAwareImpl implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null
                && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {
            MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
            return Optional.of(principal.getId());
        }

        return Optional.empty();
    }
}
