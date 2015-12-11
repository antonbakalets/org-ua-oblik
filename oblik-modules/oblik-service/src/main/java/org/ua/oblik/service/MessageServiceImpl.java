package org.ua.oblik.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendMessage(String from, String to, String subject, String msg) {
        LOGGER.info("Sending message to {}", to);
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
        try {
            mailSender.send(message);
        } catch (MailException me) {
            LOGGER.error("Error sending message.", me);
        }
    }
}
