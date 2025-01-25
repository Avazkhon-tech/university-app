package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
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
import uz.mu.lms.service.OtpService;
import uz.mu.lms.service.factory.OtpServiceFactory;
import uz.mu.lms.service.jwt.JwtProvider;
import uz.mu.lms.model.enums.OtpMethod;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final TempPasswordRepository tempPasswordRepository;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final OtpServiceFactory otpServiceFactory;

    @Override
    public Token login(LoginDto loginDto) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password()));
        } catch (BadCredentialsException e) {
            throw new AuthenticationFailureException("Username or password is incorrect");
        }

        String generatedToken = jwtProvider.generateToken(loginDto.username());
        return new Token(generatedToken);
    }


    @Override
    public void SendOTP(String username, OtpMethod otpMethod) {
        User user = getUser(otpMethod, username);
        OtpService otpService;

        otpService = otpServiceFactory.getOtpService(otpMethod);
        int generatedCode = otpService.sendOTP(username);

        // TODO this will be changed to send proper code once i find a sms provider
        TempPassword tempPassword = TempPassword
                .builder()
                .password(otpMethod.equals(OtpMethod.PHONE_NUMBER) ? "7777" : String.valueOf(generatedCode))
                .userId(user.getId())
                .build();

        tempPasswordRepository.save(tempPassword);
    }

    @Override
    public Token verifyOTP(LoginDto loginDto, OtpMethod otpMethod) {
        String username = loginDto.username();
        User user = getUser(otpMethod, username);

        Optional<TempPassword> tempPassword = tempPasswordRepository.findById(user.getId());

        if (tempPassword.isEmpty()) {
            throw new PasswordNotAcceptedException("password is expired");
        }

        if (!tempPassword.get().getPassword().equals(loginDto.password())) {
            throw new PasswordNotAcceptedException("Incorrect code");
        }

        tempPasswordRepository.deleteById(tempPassword.get().getUserId());

        String generatedToken = jwtProvider.generateToken(user.getUsername());

        return new Token(generatedToken);
    }

    @Override
    public void resetPassword(ResetPasswordDto resetPasswordDto, String token) {

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
    }


    private User getUser(OtpMethod otpMethod, String username) {
        return switch (otpMethod) {
            case EMAIL -> userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));

            case PHONE_NUMBER -> userRepository.findByPhoneNumber(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));
        };
    }
}
