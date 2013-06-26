package org.ua.oblik.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.ua.oblik.service.AccountService;

/**
 *
 * @author Anton Bakalets
 */
@Controller
public class AccountController {
    
    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);
    
    @Autowired
    private AccountService accountService;
    
    @RequestMapping("/account/list")
    public String list(final Model model) {
        LOG.debug("account list");
        model.addAttribute("assetsAccounts", accountService.getAssetsAccounts());
        return "loaded/accounts";
    }
}
