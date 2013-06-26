package org.ua.oblik.controllers;

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
public class TotalController {
    
    private static final Logger LOG = LoggerFactory.getLogger(TotalController.class);
    
    @RequestMapping("/total/currecy")
    public String totalCurrency(final Model model) {
        LOG.debug("total by currency");
        return "loaded/total-by-currency";
    }
    
    @RequestMapping("/total/account")
    public String totalAccount(final Model model) {
        LOG.debug("total by account");
        return "loaded/total-by-account";
    }
}
