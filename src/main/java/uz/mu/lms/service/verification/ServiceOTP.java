package uz.mu.lms.service.verification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class ServiceOTP implements IServiceOTP {

    private final SenderViaEmailOTP emailSender;
    private final SenderViaSmsOTP smsSender;

    public void sendOTP(String username, MethodOTP method, String code) {
        IAbstractSenderOTP verificationCodeSender;
        if (method.equals(MethodOTP.EMAIL)) {
            verificationCodeSender = emailSender;
        } else if (method.equals(MethodOTP.PHONE_NUMBER)) {
            verificationCodeSender = smsSender;
        } else {
            throw new IllegalStateException("Invalid method. Choose 'email' or 'sms'");
        }
        verificationCodeSender.sendOTP(username, code);
    }

    public String generateOTP() {
        SecureRandom rand = new SecureRandom();
        return String.valueOf(rand.nextInt(900000) + 100000);
    }
}




