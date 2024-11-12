package uz.mu.lms.service;

import uz.mu.lms.dto.LoginDto;
import uz.mu.lms.dto.ResponseDto;

public interface AuthServices {
    ResponseDto<String>login(LoginDto loginDto);

    ResponseDto<String> loginByEmailCode(String email);

    ResponseDto<String> verifyEmailCode(LoginDto loginDto);

    ResponseDto<String> getSmsCode(String phoneNumber);

    ResponseDto<String> verifySmsCode(LoginDto loginDto);
}