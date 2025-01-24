package uz.mu.lms.service.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mu.lms.service.OtpService;
import uz.mu.lms.service.impl.OtpEmailServiceImpl;
import uz.mu.lms.service.impl.OtpSmsServiceImpl;
import uz.mu.lms.model.enums.OtpMethod;

@Service
@RequiredArgsConstructor
public class OtpServiceFactory {

    private final OtpEmailServiceImpl otpEmailServiceImpl;
    private final OtpSmsServiceImpl otpSmsServiceImpl;

    public OtpService getOtpService(OtpMethod otpMethod) {
        return switch (otpMethod) {
            case EMAIL -> otpEmailServiceImpl;
            case PHONE_NUMBER -> otpSmsServiceImpl;
        };
    }
}
