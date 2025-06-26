package com.ecommerce.ea.services.email;

import com.ecommerce.ea.DTOs.email.Email;
import com.ecommerce.ea.interfaces.email.IEmail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService implements IEmail {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void SentEmail(Email email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            String htmlTemplate = readFile();
            htmlTemplate = htmlTemplate.replace("${message}", email.getBody());

            helper.setText(htmlTemplate, true);
            helper.setTo(email.getToAddress());
            helper.setFrom(email.getFromAddress());
            helper.setSubject(email.getSubject());

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar correo: " + e.getMessage(), e);
        }
    }
    private String readFile() {
        try {
            return new String(getClass().getClassLoader().getResourceAsStream("templates/TokenValidation.html").readAllBytes());
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo plantilla HTML: " + e.getMessage(), e);
        }
    }
}
