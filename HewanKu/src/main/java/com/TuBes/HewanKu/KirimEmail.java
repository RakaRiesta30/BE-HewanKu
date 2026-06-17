package com.TuBes.HewanKu;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KirimEmail {

    @Value("${sendinblue.api.key}")
    private String brevoApiKey;

    private final String SENDER_EMAIL = "rakariesta30@gmail.com";

    public String sendEmail(String email) {
        Random random = new Random();
        String otp = String.valueOf(100000 + random.nextInt(900000));

        try {
            String jsonBody = """
                    {
                       "sender": {
                          "name": "HewanKu",
                          "email": "%s"
                       },
                       "to": [
                          {
                             "email": "%s"
                          }
                       ],
                       "subject": "%s",
                       "textContent": "%s"
                    }
                    """.formatted(
                    SENDER_EMAIL,
                    email,
                    "Kode OTP HewanKu",
                    "Gunakan kode OTP ini, jangan bagikan kode OTP ini ke siapa-siapa = " + otp);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.brevo.com/v3/smtp/email"))
                    .header("api-key", brevoApiKey)
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                System.out.println("Email sukses terkirim via Brevo API ke: " + email);
                return otp;
            } else {
                System.out.println("Gagal: " + response.body());
                return response.body();
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Error sistem: " + e.getMessage());
            return e.getMessage();
        }
    }
}