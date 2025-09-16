package com.demo.global.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MailService {
    JavaMailSender mailSender;

    @Value("{spring.mail.username:test@email.com}")
    @NonFinal
    String sender;

    /**
     *
     * @param subject
     * @param content
     * @param recipient
     * @param cc
     * @param bcc
     */
    public void sendMail(
            @NonNull String subject,
            @NonNull String content,
            @Nullable String recipient,
            @Nullable String[] cc,
            @Nullable String[] bcc) {

        var message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(content);
        message.setCc(cc);
        message.setBcc(bcc);
        message.setSentDate(new Date());

        mailSender.send(message);
    }

    public void sendMailWithAttachments() {
        // TODO:
    }
}
