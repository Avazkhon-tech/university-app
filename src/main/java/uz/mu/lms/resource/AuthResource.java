package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.LoginDto;
import uz.mu.lms.dto.ResetPasswordDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.service.auth.IAuthService;
import uz.mu.lms.service.verification.MethodOTP;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthResource {

    private final IAuthService IAuthService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<String>> login(@RequestBody LoginDto loginDto) {
        return IAuthService.login(loginDto);
    }

    @PostMapping("/get-otp-email/{email}")
    public ResponseDto<String> sendOTPbyEmail(@PathVariable String email) {
        return IAuthService.SendOTP(email, MethodOTP.EMAIL);
    }

    @PostMapping("/verify-otp-email")
    public ResponseDto<String> verifyOTPEmail(@RequestBody LoginDto loginDto) {
        return IAuthService.verifyOTP(loginDto, MethodOTP.EMAIL);
    }

    @PostMapping("/get-otp-sms/{phoneNumber}")
    public ResponseDto<String> SendOTPbyPhoneNumber(@PathVariable String phoneNumber) {
        return IAuthService.SendOTP(phoneNumber, MethodOTP.PHONE_NUMBER);
    }

    @PostMapping("/verify-otp-sms")
    public ResponseDto<String> verifyOTPPhoneNumber(@RequestBody LoginDto loginDto) {
        return IAuthService.verifyOTP(loginDto, MethodOTP.PHONE_NUMBER);
    }

    @PostMapping("/reset-password")
    public ResponseDto<String> resetPassword(
            @RequestBody ResetPasswordDto resetPasswordDto,
            @RequestHeader("Authorization") String token) {

        return IAuthService.resetPassword(resetPasswordDto, token);
    }
}
