package uz.mu.lms.service;

import uz.mu.lms.dto.LoginDto;
import uz.mu.lms.dto.ResetPasswordDto;
import uz.mu.lms.dto.Token;
import uz.mu.lms.model.enums.OtpMethod;

public interface AuthService {
    Token login(LoginDto loginDto);

    void SendOTP(String email, OtpMethod method);

    Token verifyOTP(LoginDto loginDto, OtpMethod method);

    void resetPassword(ResetPasswordDto resetPasswordDto, String token);
}