package org.ua.oblik.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.ua.oblik.controllers.beans.LoginAction;

/**
 *
 * @author Anton Bakalers
 */
@Controller
public class WelcomeController {

    private static final String MESSAGE_CODE = "messageCode";

    private static final Logger LOG = LoggerFactory.getLogger(WelcomeController.class);

    @RequestMapping("/login")
    public String login(final Model model, @RequestParam(value = "action", required = false) final LoginAction action) {
        LOG.info("login, action: {}", action);
        if (action == null) {
            return "login";
        }
        switch (action) {
            case ERROR:
                model.addAttribute(MESSAGE_CODE, "login.bad.credentials");
                break;
            case LOGOUT:
                model.addAttribute(MESSAGE_CODE, "login.logout.success");
                break;
            case FORGOT:
                return "redirect:/register/forgot.html";
            case REGISTER:
                return "redirect:/register/register.html";
        }
        return "login";
    }

    @RequestMapping("/register/forgot")
    public String forgot() {
        LOG.info("Forgot password request.");
        return "forgot";
    }

    @RequestMapping("/register/register")
    public String register() {
        LOG.info("Registration requet request.");
        return "register";
    }
}
