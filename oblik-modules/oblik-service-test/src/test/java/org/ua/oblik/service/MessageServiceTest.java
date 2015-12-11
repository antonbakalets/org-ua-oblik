package org.ua.oblik.service;

import static org.junit.Assert.assertEquals;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageServiceTest extends BaseTransactionServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceTest.class);

    @Rule
    public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP);

    @Autowired
    private MessageService messageService;

    @Test
    public void testSendMessage() throws Exception {
        messageService.sendMessage("from@domain.com", "to@domain.com", "Subject", "Body");

        assertEquals("Body", GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]));
    }
}