package org.ua.oblik.mail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;
import org.ua.oblik.domain.model.UserLogin;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MailContentBuilderTest {

    private MailContentBuilder mailContentBuilder;

    @Before
    public void setUp() throws Exception {
        TemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        mailContentBuilder = new MailContentBuilder();
        mailContentBuilder.setTemplateEngine(templateEngine);
    }

    @Test
    public void testRegistrationConfirmation() {
        UserLogin userLogin = mock(UserLogin.class);
        when(userLogin.getUsername()).thenReturn("Test name");
        String message = mailContentBuilder.buildRegistrationConfirmation(userLogin);
        assertTrue(message.contains("Dear, <span>Test name</span>"));
    }
}