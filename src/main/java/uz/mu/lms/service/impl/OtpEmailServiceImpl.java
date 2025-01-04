package uz.mu.lms.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import uz.mu.lms.service.OtpService;

@Service
@Qualifier("email")
@RequiredArgsConstructor
public class OtpEmailServiceImpl implements OtpService {

    private final JavaMailSender mailSender;

    @Override
    public Integer sendOTP(String username) {

        MimeMessage message = mailSender.createMimeMessage();

        Integer code = generateOTP();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("avazkhonnazirov@yahoo.com");
            helper.setTo(username);
            helper.setSubject("Verification Code");

            String htmlContent = "<html>" +
                    "<body>" +
                    "<h1 style='color: blue;'>Here is your verification code:</h1>" +
                    "<p style='font-size: 20px;'><strong style='padding: 5px;'>" + code + "</strong></p>" +
                    "<p style='font-family: Arial, sans-serif;'>Please use this code to verify your email.</p>" +
                    "</body>" +
                    "</html>";

            helper.setText(htmlContent, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            return -1;
        }

        return code;
    }
}


