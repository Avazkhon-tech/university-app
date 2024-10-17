package uz.mu.lms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.mu.lms.dto.LoginDto;
import uz.mu.lms.dto.ResponseDto;
import uz.mu.lms.service.AuthServices;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthResource {

    private final AuthServices authServices;

    @PostMapping("/login")
    public ResponseDto<String> login(@RequestBody LoginDto loginDto) {
        return authServices.login(loginDto);
    }

    @PostMapping("/get-code-email/{email}")
    public ResponseDto<String> loginByEmailCode(@PathVariable String email) {
        return authServices.loginByEmailCode(email);
    }

    @PostMapping("/verify-code-email")
    public ResponseDto<String> verifyEmailCode(@RequestBody LoginDto loginDto) {
        return authServices.verifyEmailCode(loginDto);
    }

    @PostMapping("/get-code-sms/{phoneNumber}")
    public ResponseDto<String> getSmsCode(@PathVariable String phoneNumber) {
        return authServices.getSmsCode(phoneNumber);
    }

    @PostMapping("/verify-code-sms")
    public ResponseDto<String> verifySmsCode(@RequestBody LoginDto loginDto) {
        return authServices.verifySmsCode(loginDto);
    }
}
