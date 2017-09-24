package org.ua.oblik.mail;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.ua.oblik.domain.model.UserLogin;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MailSenderServiceTest {

    private GreenMail smtpServer;

    @InjectMocks
    private MailSenderService mailSenderService;

    @Mock
    private MailContentBuilder mailContentBuilder;

    @Before
    public void setUp() throws Exception {

        smtpServer = new GreenMail(new ServerSetup(25, null, "smtp"));
        smtpServer.start();

        JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
        mailSenderService.setMailSender(emailSender);
    }

    @Test
    public void shouldSendMail() throws Exception {
        String message = "Test message content";
        when(mailContentBuilder.buildRegistrationConfirmation(any())).thenReturn(message);

        UserLogin userLogin= mock(UserLogin.class);
        when(userLogin.getEmail()).thenReturn("an.email@host.com");
        mailSenderService.sendConfirmation(userLogin);

        assertReceivedMessageContains(message);
    }

    private void assertReceivedMessageContains(String expected) throws IOException, MessagingException {
        MimeMessage[] receivedMessages = smtpServer.getReceivedMessages();
        assertEquals(1, receivedMessages.length);
        String content = (String) receivedMessages[0].getContent();
        assertTrue(content.contains(expected));
    }

    @After
    public void tearDown() throws Exception {
        smtpServer.stop();
    }
}
