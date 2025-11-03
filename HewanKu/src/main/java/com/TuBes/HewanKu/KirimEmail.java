package com.TuBes.HewanKu;

import java.util.*;

import org.springframework.stereotype.Service;

import jakarta.mail.*;
import jakarta.mail.internet.*;

@Service
public class KirimEmail {
    public String sendEmail(String email) {
        Random random = new Random();
        String otp = String.valueOf(100000 + random.nextInt(900000));
        String recipient = email;

        String sender = "rakariesta30@gmail.com";

        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);

        properties.put("mail.smtp.port", "587");

        properties.put("mail.smtp.auth", "true");

        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, "pmiq wcyo jrej puca");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(sender));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            message.setSubject("Kode OTP");

            message.setText("Gunakan kode OTP ini, jangan bagian kode OTP ini ke siapa - siapa = " + otp);

            Transport.send(message);
            System.out.println("Mail successfully sent");
            return otp;
        } catch (MessagingException mex) {
            System.out.println("Gagal mengirim email: " + mex.getMessage());
            return "Salah";
        }
    }
}