package org.ua.oblik.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.beans.AccountVO;

/**
 *
 * @author Anton Bakalets
 */
@Controller
public class AccountController {
    
    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);
    
    private static final String ASSETS_ACCOUNTS   = "assetsAccounts";
    
    private static final String INCOME_ACCOUNTS = "incomeAccounts";
    
    private static final String EXPENSE_ACCOUNTS = "expenseAccounts";
    
    @Autowired
    private AccountService accountService;
    
    @RequestMapping("/account/list")
    public String list(final Model model) {
        LOG.debug("account list");
        List<AccountVO> assetsAccountsList = accountService.getAssetsAccounts();
        model.addAttribute(ASSETS_ACCOUNTS , assetsAccountsList);
        List<AccountVO> incomeAccountsList = accountService.getIncomeAccounts();
        model.addAttribute(INCOME_ACCOUNTS , incomeAccountsList);
        List<AccountVO> expenseAccountsList = accountService.getExpenseAccounts();
        model.addAttribute(EXPENSE_ACCOUNTS , expenseAccountsList);
        return "loaded/accounts";
    }
}
