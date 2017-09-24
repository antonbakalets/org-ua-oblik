package org.ua.oblik.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.ua.oblik.domain.model.UserLogin;

@Service
public class MailContentBuilder {

    private TemplateEngine templateEngine;

    public String buildRegistrationConfirmation(UserLogin userLogin) {
        Context context = new Context();
        context.setVariable("name", userLogin.getUsername());
        return templateEngine.process("confirm", context);
    }

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
}
