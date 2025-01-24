package uz.mu.lms.service;

import org.springframework.http.ResponseEntity;
import uz.mu.lms.dto.LoginDto;
import uz.mu.lms.dto.ResetPasswordDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.Token;
import uz.mu.lms.model.enums.OtpMethod;

public interface AuthService {
    ResponseEntity<ResponseDto<Token>> login(LoginDto loginDto);

    ResponseDto<String> SendOTP(String email, OtpMethod method);

    ResponseEntity<ResponseDto<Token>> verifyOTP(LoginDto loginDto, OtpMethod method);

    ResponseDto<String> resetPassword(ResetPasswordDto resetPasswordDto, String token);
}