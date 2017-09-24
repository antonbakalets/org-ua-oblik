package org.ua.oblik.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.ua.oblik.domain.model.UserLogin;

@Service
public class MailSenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailSenderService.class);

    private JavaMailSender mailSender;

    private MailContentBuilder mailContentBuilder;

    public void sendConfirmation(UserLogin userLogin) {
        String confirmation = mailContentBuilder.buildRegistrationConfirmation(userLogin);
        String email = userLogin.getEmail();
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("sample@sample.com");
            messageHelper.setTo(email);
            messageHelper.setSubject("Sample mail subject");
            messageHelper.setText(confirmation);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            LOGGER.error("Error sending email.", e);
        }
    }

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Autowired
    public void setMailContentBuilder(MailContentBuilder mailContentBuilder) {
        this.mailContentBuilder = mailContentBuilder;
    }
}
