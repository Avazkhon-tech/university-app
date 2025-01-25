package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.LoginDto;
import uz.mu.lms.dto.ResetPasswordDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.dto.Token;
import uz.mu.lms.service.AuthService;
import uz.mu.lms.model.enums.OtpMethod;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthResource {

    private final AuthService authService;

    // FOR ALL
    @PostMapping("/login")
    public ResponseDto<Token> login(@RequestBody LoginDto loginDto) {
        return ResponseDto.success(authService.login(loginDto), "Successfully logged in");
    }

    @PostMapping("/get-otp-email/{email}")
    public ResponseDto<?> sendOTPbyEmail(@PathVariable String email) {
        authService.SendOTP(email, OtpMethod.EMAIL);
        return ResponseDto.success("Verification code has been sent to your email");
    }

    @PostMapping("/verify-otp-email")
    public ResponseDto<Token> verifyOTPEmail(@RequestBody LoginDto loginDto) {
        Token token = authService.verifyOTP(loginDto, OtpMethod.EMAIL);
        return ResponseDto.success(token, "Successfully verified");
    }

    @PostMapping("/get-otp-sms/{phoneNumber}")
    public ResponseDto<String> SendOTPbyPhoneNumber(@PathVariable String phoneNumber) {
        authService.SendOTP(phoneNumber, OtpMethod.PHONE_NUMBER);
        return ResponseDto.success("Verification code has been sent to your phone number");
    }

    @PostMapping("/verify-otp-sms")
    public ResponseDto<Token> verifyOTPPhoneNumber(@RequestBody LoginDto loginDto) {
        Token token = authService.verifyOTP(loginDto, OtpMethod.PHONE_NUMBER);
        return ResponseDto.success(token, "Successfully verified");
    }

    @PostMapping("/reset-password")
    public ResponseDto<String> resetPassword(
            @RequestBody ResetPasswordDto resetPasswordDto,
            @RequestHeader("Authorization") String token) {

        authService.resetPassword(resetPasswordDto, token);
        return ResponseDto.success("Password has been reset");
    }
}
