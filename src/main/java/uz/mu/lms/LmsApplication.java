package uz.mu.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
public class LmsApplication {
	public static void main(String[] args)
	{
		SpringApplication.run(LmsApplication.class, args);
		SecurityContext contextHolder = SecurityContextHolder.createEmptyContext();
		Authentication authentication = new TestingAuthenticationToken("user", "user", "admin");
		contextHolder.setAuthentication(authentication);
		SecurityContextHolder.setContext(contextHolder);

	}

}
