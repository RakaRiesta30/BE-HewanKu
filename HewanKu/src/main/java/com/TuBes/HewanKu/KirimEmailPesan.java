package com.TuBes.HewanKu;

import org.springframework.stereotype.Service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

@Service
public class KirimEmailPesan {
    public void sendEmail(String email, String pesan, String subject) {
        Resend resend = new Resend("re_15g489oV_5NNQEdagQQtriJUEdHJFJG3W");

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("onboarding@resend.dev")
                .to("rakariesta30@gmail.com")
                .subject(subject)
                .html(pesan)
                .build();
        try {
            CreateEmailResponse data = resend.emails().send(params);
            System.out.println(data.getId());
        } catch (ResendException e) {
            e.printStackTrace();
        }
    }
}