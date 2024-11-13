package uz.mu.lms.service.verification;

public interface IAbstractSenderOTP {
    void sendOTP(String username, String code);
}
