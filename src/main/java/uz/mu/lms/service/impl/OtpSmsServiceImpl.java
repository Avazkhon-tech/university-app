package uz.mu.lms.service.impl;

import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.mu.lms.service.OtpService;

import java.io.IOException;

@Service
@Qualifier("sms")
@RequiredArgsConstructor
public class OtpSmsServiceImpl implements OtpService {

    private final OkHttpClient client;
    @Value("${infobip.api.url}")
    private String apiUrl;
    @Value("${infobip.api.key}")
    private String apiKey;

    OtpSmsServiceImpl() {
        this.client = new OkHttpClient();
    }

    public Integer sendOTP(String phoneNumber) {

        Integer code = generateOTP();

        String message = "Your verification code is: " + code;
        MediaType mediaType = MediaType.parse("application/json");

        String jsonBody = String.format("""
            {
                "messages": [
                    {
                        "destinations": [
                            {"to": "%s"}
                        ],
                        "from": "447491163443",
                        "text": "%s"
                    }
                ]
            }
        """, phoneNumber, message);

        RequestBody body = RequestBody.create(mediaType, jsonBody);

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .addHeader("Authorization", "App " + apiKey)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            return -1;
        }

        return code;
    }
}
