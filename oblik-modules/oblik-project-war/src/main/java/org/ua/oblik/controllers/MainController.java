package org.ua.oblik.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.EntityNotFoundException;

/**
 *
 * @author Anton Bakalets
 */
@Controller
public class MainController {
    
    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);
    
    @Autowired
    private CurrencyService currencyService;
    
    @RequestMapping("/main")
    public String welcome(final Model model) {
        LOG.debug("main");
        try {
            currencyService.getDefaultCurrency();
            model.addAttribute("defaultCurrencyNotExists", Boolean.FALSE);
        } catch (EntityNotFoundException enfe) {
            model.addAttribute("defaultCurrencyNotExists", Boolean.TRUE);
        }
        return "layout";
    }
}
