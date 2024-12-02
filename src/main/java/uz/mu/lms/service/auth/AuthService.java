package uz.mu.lms.service.auth;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.LoginDto;
import uz.mu.lms.dto.ResetPasswordDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.exceptions.AuthenticationFailureException;
import uz.mu.lms.exceptions.PasswordNotAcceptedException;
import uz.mu.lms.model.User;
import uz.mu.lms.model.redis.TempPassword;
import uz.mu.lms.repository.UserRepository;
import uz.mu.lms.repository.redis.TempPasswordRepository;
import uz.mu.lms.resource.AuthResource;
import uz.mu.lms.service.jwt.JwtProvider;
import uz.mu.lms.service.verification.MethodOTP;
import uz.mu.lms.service.verification.ServiceOTP;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final ServiceOTP serviceOTP;
    private final TempPasswordRepository tempPasswordRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthResource.class);

    @Override
    public ResponseEntity<ResponseDto<String>> login(LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password()));
        } catch (BadCredentialsException e) {
            logger.info("Could not authenticate user: {}", loginDto.username());
            throw new AuthenticationFailureException("Username or password is incorrect");
        }

        logger.info("User {} logged in successfully", loginDto.username());
        String generatedToken = jwtProvider.generateToken(loginDto.username());
        ResponseDto<String> responseDto = ResponseDto.<String>builder()
                .code(200)
                .message("Successfully logged in")
                .data(generatedToken)
                .success(true)
                .build();
        return ResponseEntity.ok(responseDto);
    }
    @Override
    public ResponseDto<String> SendOTP(String username, MethodOTP methodOTP) {
        User user = null;
        if (methodOTP.equals(MethodOTP.EMAIL)) {
            user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));
        } else if (methodOTP.equals(MethodOTP.PHONE_NUMBER)) {
            user = userRepository.findByPhoneNumber(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));
        }

        String generatedCode = serviceOTP.generateOTP();


        TempPassword tempPassword = TempPassword
                .builder()
                .password(generatedCode)
                .userId(user.getId())
                .build();

        tempPasswordRepository.save(tempPassword);

        serviceOTP.sendOTP(username, methodOTP, generatedCode);

        return ResponseDto.<String>builder()
                .code(200)
                .success(true)
                .message("Verification code has been sent to your "
                        + methodOTP.toString().toLowerCase().replace("_", " "))
                .build();
    }

    @Override
    public ResponseEntity<ResponseDto<String>> verifyOTP(LoginDto loginDto, MethodOTP methodOTP) {
        User user = null;
        if (methodOTP.equals(MethodOTP.EMAIL)) {
            user = userRepository.findByUsername(loginDto.username())
                    .orElseThrow(() -> new UsernameNotFoundException(loginDto.username()));
        } else if (methodOTP.equals(MethodOTP.PHONE_NUMBER)) {
            user = userRepository.findByPhoneNumber(loginDto.username())
                    .orElseThrow(() -> new UsernameNotFoundException(loginDto.username()));
        }

        Optional<TempPassword> tempPassword = tempPasswordRepository.findById(user.getId());

        if (tempPassword.isEmpty()) {
           throw new PasswordNotAcceptedException("password is expired");
       }

        if (!tempPassword.get().getPassword().equals(loginDto.password())) {
            throw new PasswordNotAcceptedException("Incorrect code");
        }

        tempPasswordRepository.deleteById(tempPassword.get().getUserId());
        String generatedToken = jwtProvider.generateToken(loginDto.username());
        return ResponseEntity.ok()
                .body(ResponseDto.<String>builder()
                .code(200)
                .data(generatedToken)
                .message("Successfully verified")
                .success(true)
                .build());
    }

    @Override
    public ResponseDto<String> resetPassword(ResetPasswordDto resetPasswordDto, String token) {;

        String username = jwtProvider.extractUserName(token.replace("Bearer ", ""));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        if (!resetPasswordDto.password().equals(resetPasswordDto.passwordConfirmation())) {
            throw new PasswordNotAcceptedException("Passwords do not match");
        }

        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        if (!resetPasswordDto.password().matches(passwordRegex)) {
            throw new PasswordNotAcceptedException("Password is not complex enough");
        }

        user.setPassword(resetPasswordDto.password());
        userRepository.save(user);
        return ResponseDto.<String>builder()
                .code(200)
                .message("Successfully updated the password")
                .success(true)
                .build();
    }
}
