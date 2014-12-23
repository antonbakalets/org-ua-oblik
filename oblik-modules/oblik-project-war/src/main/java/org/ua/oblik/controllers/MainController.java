package org.ua.oblik.controllers;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Anton Bakalets
 */
@Controller
public class MainController {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    @RequestMapping("/main")
    public String welcome(final Locale locale, final Model model) {
        LOG.debug("main");
        return "layout";
    }
}
