package org.ua.oblik.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Anton Bakalers
 */
@Controller
public class WelcomeController {

    private static final String ERROR = "error";

    private static final String LOGOUT = "logout";

    private static final String MESSAGE_CODE = "messageCode";

    private static final Logger LOG = LoggerFactory.getLogger(WelcomeController.class);

    @RequestMapping("/login")
    public String login(final Model model, @RequestParam(value = "action", required = false) final String action) {
        LOG.info("login, action: ", action);

        if (StringUtils.hasText(action)) {
            if (ERROR.equals(action)) {
                model.addAttribute(MESSAGE_CODE, "login.bad.credentials");
            }
            if (LOGOUT.equals(action)) {
                model.addAttribute(MESSAGE_CODE, "login.logout.success");
            }
        }
        return "login";
    }
}
