package uz.mu.lms.service.auth;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.LoginDto;
import uz.mu.lms.dto.ResetPasswordDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.exceptions.PasswordNotAcceptedException;
import uz.mu.lms.model.TempPassword;
import uz.mu.lms.model.User;
import uz.mu.lms.repository.TempPasswordRepository;
import uz.mu.lms.repository.UserRepository;
import uz.mu.lms.resource.AuthResource;
import uz.mu.lms.service.jwt.JwtProvider;
import uz.mu.lms.service.verification.MethodOTP;
import uz.mu.lms.service.verification.ServiceOTP;

import java.time.LocalDateTime;
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
    public ResponseDto<String> login(LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password()));
        } catch (BadCredentialsException e) {
            logger.info("Could not authenticate user: {}", loginDto.username());
            return ResponseDto.<String>builder()
                    .code(400)
                    .data(e.getMessage())
                    .message("username or password is incorrect")
                    .success(false)
                    .build();
        }


        logger.info("User {} logged in successfully", loginDto.username());
        String generatedToken = jwtProvider.generateToken(loginDto.username());
        return ResponseDto.<String>builder()
                .code(200)
                .data(generatedToken)
                .success(true)
                .build();
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
                .generatedPassword(generatedCode)
                .expirationDate(LocalDateTime.now().plusMinutes(3))
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
    public ResponseDto<String> verifyOTP(LoginDto loginDto, MethodOTP methodOTP) {
        User user = null;
        if (methodOTP.equals(MethodOTP.EMAIL)) {
            user = userRepository.findByUsername(loginDto.username())
                    .orElseThrow(() -> new UsernameNotFoundException(loginDto.username()));
        } else if (methodOTP.equals(MethodOTP.PHONE_NUMBER)) {
            user = userRepository.findByPhoneNumber(loginDto.username())
                    .orElseThrow(() -> new UsernameNotFoundException(loginDto.username()));
        }

        Optional<TempPassword> tempPassword = tempPasswordRepository.findByUserId(user.getId());


       if (tempPassword.isEmpty() || tempPassword.get().getExpirationDate().isBefore(LocalDateTime.now())) {
           throw new PasswordNotAcceptedException("password is expired");
       }

        if (!tempPassword.get().getGeneratedPassword().equals(loginDto.password())) {
            return ResponseDto.<String>builder()
                    .code(400)
                    .message("password is incorrect")
                    .success(false)
                    .build();
        }

        tempPasswordRepository.deleteById(tempPassword.get().getUserId());
        String generatedToken = jwtProvider.generateToken(loginDto.username());
        return ResponseDto.<String>builder()
                .code(200)
                .data(generatedToken)
                .message("Successfully verified")
                .success(true)
                .build();
    }

    @Override
    public ResponseDto<String> resetPassword(ResetPasswordDto resetPasswordDto, String token) {;

        String username = jwtProvider.extractUserName(token.replace("Bearer ", ""));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        if (!resetPasswordDto.password().equals(resetPasswordDto.passwordConfirmation())) {
            throw new PasswordNotAcceptedException("passwords do not match");
        }

        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        if (!resetPasswordDto.password().matches(passwordRegex)) {
            throw new PasswordNotAcceptedException("password is not complex enough");
        }

        // TODO add encryption later
        user.setPassword(resetPasswordDto.password());
        userRepository.save(user);
        return ResponseDto.<String>builder()
                .code(200)
                .message("Successfully updated the password")
                .success(true)
                .build();
    }
}
