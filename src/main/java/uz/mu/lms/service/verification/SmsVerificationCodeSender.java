package uz.mu.lms.service.verification;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SmsVerificationCodeSender implements VerificationCodeSender {

    private final String ACCOUNT_SID = "AC7f765a69adb5d20442154d382de253b0";
    private final String AUTH_TOKEN = "3709472649cdaf6045163687fab50e7e";
    private final String FROM_PHONE_NUMBER = "+13057482352";

    public SmsVerificationCodeSender() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @Override
    public void sendVerificationCode(String phoneNumber, String code) {
        Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(FROM_PHONE_NUMBER),
                "Your verification code is: " + code).create();
    }
}