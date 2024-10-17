package uz.mu.lms.service.verification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class VerificationCodeService {

    private final SecureRandom random = new SecureRandom();
    private final EmailVerificationCodeSender emailSender;
    private final SmsVerificationCodeSender smsSender;

    public void SendCode(String username, String code, String method) {
        VerificationCodeSender verificationCodeSender;
        if (method.equals("email")) {
            verificationCodeSender = emailSender;
        } else if (method.equals("sms")) {
            verificationCodeSender = smsSender;
        } else {
            throw new IllegalStateException("Invalid method. Choose 'email' or 'sms'");
        }

        verificationCodeSender.sendVerificationCode(username, code);
    }

    public String generateCode() {
        int codeLength = 8;
        StringBuilder code = new StringBuilder(codeLength);

        for (int i = 0; i < codeLength; i++) {
            int digit = random.nextInt(10);
            code.append(digit);
        }
        return code.toString();
    }

}
