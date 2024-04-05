package com.parkingcomestrue.auth.authcode.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MailAuthCodeSender implements AuthCodeSender {

    private final JavaMailSender mailSender;

    @Override
    public void send(String destination, String authCode) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(destination);
        mailMessage.setSubject("인증 코드");
        mailMessage.setText(authCode);
        mailSender.send(mailMessage);
    }
}
