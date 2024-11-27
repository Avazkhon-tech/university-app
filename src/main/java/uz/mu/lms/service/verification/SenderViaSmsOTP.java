package uz.mu.lms.service.verification;

import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@RequiredArgsConstructor
class SenderViaSmsOTP  implements IAbstractSenderOTP {

    private static final Logger log = LoggerFactory.getLogger(SenderViaSmsOTP.class);
    private final OkHttpClient client;
    @Value("${infobip.api.url}")
    private String apiUrl;
    @Value("${infobip.api.key}")
    private String apiKey;

    SenderViaSmsOTP() {
        this.client = new OkHttpClient();
    }

    public void sendOTP(String toPhoneNumber, String code) {
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
        """, toPhoneNumber, message);

        RequestBody body = RequestBody.create(mediaType, jsonBody);
        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .addHeader("Authorization", "App " + apiKey)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
