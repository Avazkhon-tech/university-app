package uz.mu.lms.service.verification;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SenderViaSmsOTP implements IAbstractSenderOTP {

    public SenderViaSmsOTP() {
        String ACCOUNT_SID = "ACb4866d4925a8c88bebfc8e8a7d663426";
        String AUTH_TOKEN = "d7e64ebc4ab975c9e57fa73c935f4356";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @Override
    public void sendOTP(String phoneNumber, String code) {
        String FROM_PHONE_NUMBER = "+19789157685";
        Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(FROM_PHONE_NUMBER),
                "Your verification code is: " + code).create();
    }
}