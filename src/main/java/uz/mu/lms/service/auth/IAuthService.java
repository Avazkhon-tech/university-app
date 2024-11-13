package uz.mu.lms.service.auth;

import uz.mu.lms.dto.LoginDto;
import uz.mu.lms.dto.ResetPasswordDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.service.verification.MethodOTP;

public interface IAuthService {
    ResponseDto<String>login(LoginDto loginDto);

    ResponseDto<String> SendOTP(String email, MethodOTP method);

    ResponseDto<String> verifyOTP(LoginDto loginDto, MethodOTP method);

    ResponseDto<String> resetPassword(ResetPasswordDto resetPasswordDto, String token);
}