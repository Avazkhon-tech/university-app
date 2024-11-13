package uz.mu.lms.service.verification;

public interface IServiceOTP {

    String generateOTP();

    void sendOTP(String username, MethodOTP method, String code);
}
