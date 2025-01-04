package uz.mu.lms.service;

import java.security.SecureRandom;

public interface OtpService {

    Integer sendOTP(String username);

    default Integer generateOTP() {
        SecureRandom rand = new SecureRandom();
        return rand.nextInt(9000) + 999;
    }
}
