package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.LoginDto;
import uz.mu.lms.dto.ResetPasswordDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.Token;
import uz.mu.lms.exceptions.AuthenticationFailureException;
import uz.mu.lms.exceptions.PasswordNotAcceptedException;
import uz.mu.lms.model.User;
import uz.mu.lms.model.redis.TempPassword;
import uz.mu.lms.repository.UserRepository;
import uz.mu.lms.repository.redis.TempPasswordRepository;
import uz.mu.lms.service.AuthService;
import uz.mu.lms.service.jwt.JwtProvider;
import uz.mu.lms.utils.OtpMethod;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final TempPasswordRepository tempPasswordRepository;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    // TODO figure out a proper way to handle this situation
    private final OtpEmailServiceImpl otpViaEmailService;
    private final OtpSmsServiceImpl otpViaSmsService;

    @Override
    public ResponseEntity<ResponseDto<Token>> login(LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password()));
        } catch (BadCredentialsException e) {
            throw new AuthenticationFailureException("Username or password is incorrect");
        }

        String generatedToken = jwtProvider.generateToken(loginDto.username());
        ResponseDto<Token> responseDto = ResponseDto.<Token>builder()
                .code(200)
                .message("Successfully logged in")
                .data(new Token(generatedToken))
                .success(true)
                .build();
        return ResponseEntity.ok(responseDto);
    }


    @Override
    public ResponseDto<String> SendOTP(String username, OtpMethod otpMethod) {
        User user = null;
        int generatedCode = 0;

        if (otpMethod.equals(OtpMethod.EMAIL)) {
            user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));

            generatedCode  = otpViaEmailService.sendOTP(username);

        } else if (otpMethod.equals(OtpMethod.PHONE_NUMBER)) {
            user = userRepository.findByPhoneNumber(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));

            generatedCode = otpViaSmsService.sendOTP(username);
        }



        // TODO this will be changed to send proper code once i find a sms provider
        TempPassword tempPassword = TempPassword
                .builder()
                .password(otpMethod.equals(OtpMethod.PHONE_NUMBER)? "7777" : String.valueOf(generatedCode))
                .userId(user.getId())
                .build();

        tempPasswordRepository.save(tempPassword);

        return ResponseDto.<String>builder()
                .code(200)
                .success(true)
                .message("Verification code has been sent to your "
                        + otpMethod.toString().toLowerCase().replace("_", " "))
                .build();
    }

    @Override
    public ResponseEntity<ResponseDto<Token>> verifyOTP(LoginDto loginDto, OtpMethod otpMethod) {
        User user = null;
        if (otpMethod.equals(OtpMethod.EMAIL)) {
            user = userRepository.findByUsername(loginDto.username())
                    .orElseThrow(() -> new UsernameNotFoundException(loginDto.username()));
        } else if (otpMethod.equals(OtpMethod.PHONE_NUMBER)) {
            user = userRepository.findByPhoneNumber(loginDto.username())
                    .orElseThrow(() -> new UsernameNotFoundException(loginDto.username()));
        }


        Optional<TempPassword> tempPassword = tempPasswordRepository.findById(user.getId());

        if (tempPassword.isEmpty()) {
           throw new PasswordNotAcceptedException("password is expired");
       }

        if(!tempPassword.get().getPassword().equals(loginDto.password())) {
            throw new PasswordNotAcceptedException("Incorrect code");
        }

        tempPasswordRepository.deleteById(tempPassword.get().getUserId());
        String generatedToken = jwtProvider.generateToken(user.getUsername());
        return ResponseEntity.ok()
                .body(ResponseDto.<Token>builder()
                .code(200)
                .data(new Token(generatedToken))
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

        user.setPassword(passwordEncoder.encode(resetPasswordDto.password()));
        userRepository.save(user);
        return ResponseDto.<String>builder()
                .code(200)
                .message("Successfully updated the password")
                .success(true)
                .build();
    }
}
