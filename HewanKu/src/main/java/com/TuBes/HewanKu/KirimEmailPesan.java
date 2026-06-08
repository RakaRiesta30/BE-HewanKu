package com.TuBes.HewanKu;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

@Service
public class KirimEmailPesan {

    private final String BREVO_API_KEY = "xkeysib-KODE_RAHASIA_BREVO_KAMU";

    private final String SENDER_EMAIL = "rakariesta30@gmail.com";

    public void sendEmail(String email, String pesan, String subject) {
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
                    """.formatted(SENDER_EMAIL, email, subject, pesan);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.brevo.com/v3/smtp/email"))
                    .header("api-key", BREVO_API_KEY) // Header untuk Brevo
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                System.out.println("Email pesan sukses terkirim via Brevo API ke: " + email);
            } else {
                System.out.println("Gagal mengirim pesan dari Brevo: " + response.body());
            }

        } catch (Exception e) {
            System.out.println("Error sistem: " + e.getMessage());
        }
    }
}