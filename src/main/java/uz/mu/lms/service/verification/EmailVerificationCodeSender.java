package uz.mu.lms.service.verification;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailVerificationCodeSender implements VerificationCodeSender {

    private final JavaMailSender mailSender;

    @Override
    public void sendVerificationCode(String username, String code) {
        MimeMessage message = mailSender.createMimeMessage();

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
        }
    }
}


