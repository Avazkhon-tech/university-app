package uz.mu.lms.resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class Test {

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/hi")
    public String index() {
        return "Hello admin";
    }
}
