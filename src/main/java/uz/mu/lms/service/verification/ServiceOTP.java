package uz.mu.lms.service.verification;

public interface ServiceOTP {

    String generateOTP();

    void sendOTP(String username, MethodOTP method, String code);
}
d