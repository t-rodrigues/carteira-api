package dev.thiagorodrigues.carteira.infra.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    @Value("${from.email.address}")
    private String fromEmailAddress;

    private final JavaMailSender mailSender;

    @Async
    public void sendMail(String recipient, String subject, String content) {
        SimpleMailMessage email = new SimpleMailMessage();

        email.setFrom(fromEmailAddress);
        email.setTo(recipient);
        email.setSubject(subject);
        email.setText(content);

        mailSender.send(email);
    }

}
