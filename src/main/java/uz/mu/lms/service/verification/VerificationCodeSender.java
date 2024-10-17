package uz.mu.lms.service.verification;

public interface VerificationCodeSender {
    void sendVerificationCode(String username, String code);
}
