package com.example.bloodbank.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class EmailBuilder {
    private String to;
    private String subject;
    private String message;

    public EmailBuilder to(String to) {
        this.to = to;
        return this;
    }

    public EmailBuilder subject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailBuilder message(String message) {
        this.message = message;
        return this;
    }

    public MimeMessage buildHtml(JavaMailSender mailSender) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(this.to);
        helper.setSubject(this.subject);
        helper.setText(this.message, true); // Set HTML content, passing `true` as the second argument
        return mimeMessage;
    }
}