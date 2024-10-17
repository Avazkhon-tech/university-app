package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.mu.lms.dto.LoginDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.model.TemporaryPassword;
import uz.mu.lms.model.User;
import uz.mu.lms.repository.TemporaryPasswordRepository;
import uz.mu.lms.repository.UserRepository;
import uz.mu.lms.service.AuthServices;
import uz.mu.lms.service.jwt.JwtProvider;
import uz.mu.lms.service.verification.VerificationCodeService;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServicesImpl implements AuthServices {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final VerificationCodeService verificationCodeService;
    private final TemporaryPasswordRepository tempPasswordRepository;
    private final UserRepository userRepository;
    private final TemporaryPasswordRepository temporaryPasswordRepository;


    @Override
    public ResponseDto<String> login(LoginDto loginDto) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseDto.<String>builder()
                    .code(400)
                    .data(e.getMessage())
                    .message("username or password is incorrect")
                    .success(false)
                    .build();
        }


        String generatedToken = jwtProvider.generateToken(loginDto.getUsername());
        return ResponseDto.<String>builder()
                .code(200)
                .data(generatedToken)
                .success(true)
                .build();
    }

    @Override
    public ResponseDto<String> loginByEmailCode(String email) {
        User user = userRepository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        String generatedCode = verificationCodeService.generateCode();

        Optional<TemporaryPassword> byUserId = tempPasswordRepository.findByUserId(user.getId());
        if (byUserId.isPresent()) {
            TemporaryPassword temporaryPassword = byUserId.get();
            temporaryPassword.setGeneratedPassword(generatedCode);
            temporaryPasswordRepository.save(temporaryPassword);
        } else {
            tempPasswordRepository.save(TemporaryPassword.builder()
                    .user(user)
                    .generatedPassword(generatedCode).build());
        }

        verificationCodeService.SendCode(email, generatedCode, "email");

        return ResponseDto.<String>builder()
                .code(200)
                .success(true)
                .message("Verification code has been sent to your email")
                .build();
    }

    @Override
    public ResponseDto<String> verifyEmailCode(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(loginDto.getUsername()));

        TemporaryPassword temporaryPassword = tempPasswordRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NoSuchElementException(loginDto.getUsername()));

        if(!temporaryPassword.getGeneratedPassword().equals(loginDto.getPassword())){
            return ResponseDto.<String>builder()
                    .code(400)
                    .message("password is incorrect")
                    .success(false)
                    .build();
        }

        String generatedToken = jwtProvider.generateToken(loginDto.getUsername());
        return ResponseDto.<String>builder()
                .code(200)
                .data(generatedToken)
                .success(true)
                .build();

    }

    @Override
    public ResponseDto<String> getSmsCode(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException(phoneNumber));

        String generatedCode = verificationCodeService.generateCode();

        Optional<TemporaryPassword> byUserId = tempPasswordRepository.findByUserId(user.getId());
        if (byUserId.isPresent()) {
            TemporaryPassword temporaryPassword = byUserId.get();
            temporaryPassword.setGeneratedPassword(generatedCode);
            temporaryPasswordRepository.save(temporaryPassword);
        } else {
            tempPasswordRepository.save(TemporaryPassword.builder()
                    .user(user)
                    .generatedPassword(generatedCode).build());
        }

        verificationCodeService.SendCode(phoneNumber, generatedCode, "sms");

        return ResponseDto.<String>builder()
                .code(200)
                .success(true)
                .message("Verification code has been sent to your phone number")
                .build();
    }

    @Override
    public ResponseDto<String> verifySmsCode(LoginDto loginDto) {
        User user = userRepository.findByPhoneNumber(loginDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(loginDto.getUsername()));

        TemporaryPassword temporaryPassword = tempPasswordRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NoSuchElementException(loginDto.getUsername()));

        if(!temporaryPassword.getGeneratedPassword().equals(loginDto.getPassword())){
            return ResponseDto.<String>builder()
                    .code(400)
                    .message("password is incorrect")
                    .success(false)
                    .build();
        }

        String generatedToken = jwtProvider.generateToken(loginDto.getUsername());
        return ResponseDto.<String>builder()
                .code(200)
                .data(generatedToken)
                .success(true)
                .build();

    }

}
