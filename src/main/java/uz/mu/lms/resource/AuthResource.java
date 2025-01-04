package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.LoginDto;
import uz.mu.lms.dto.ResetPasswordDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.Token;
import uz.mu.lms.service.AuthService;
import uz.mu.lms.utils.OtpMethod;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthResource {

    private final AuthService authService;

    // FOR ALL
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<Token>> login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("/get-otp-email/{email}")
    public ResponseDto<String> sendOTPbyEmail(@PathVariable String email) {
        return authService.SendOTP(email, OtpMethod.EMAIL);
    }

    @PostMapping("/verify-otp-email")
    public ResponseEntity<ResponseDto<Token>> verifyOTPEmail(@RequestBody LoginDto loginDto) {
        return authService.verifyOTP(loginDto, OtpMethod.EMAIL);
    }

    @PostMapping("/get-otp-sms/{phoneNumber}")
    public ResponseDto<String> SendOTPbyPhoneNumber(@PathVariable String phoneNumber) {
        return authService.SendOTP(phoneNumber, OtpMethod.PHONE_NUMBER);
    }

    @PostMapping("/verify-otp-sms")
    public ResponseEntity<ResponseDto<Token>> verifyOTPPhoneNumber(@RequestBody LoginDto loginDto) {
        return authService.verifyOTP(loginDto, OtpMethod.PHONE_NUMBER);
    }

    @PostMapping("/reset-password")
    public ResponseDto<String> resetPassword(
            @RequestBody ResetPasswordDto resetPasswordDto,
            @RequestHeader("Authorization") String token) {

        return authService.resetPassword(resetPasswordDto, token);
    }
}
