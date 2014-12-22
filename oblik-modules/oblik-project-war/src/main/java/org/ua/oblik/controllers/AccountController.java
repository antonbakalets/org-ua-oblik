package org.ua.oblik.controllers;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.ua.oblik.controllers.beans.AccountBean;
import org.ua.oblik.controllers.beans.AccountOption;
import org.ua.oblik.controllers.utils.ValidationErrorLoger;
import org.ua.oblik.controllers.validators.AccountValidator;
import org.ua.oblik.service.beans.AccountVOType;

/**
 *
 * @author Anton Bakalets
 */
@Controller
public class AccountController {

    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    private static final String ASSETS = "ASSETS";

    private static final String INCOME_ACCOUNTS = "incomeAccounts";

    private static final String EXPENSE_ACCOUNTS = "expenseAccounts";

    private static final String ACCOUNT_BEAN = "accountBean";

    private static final String CURRENCY_LIST = "currencyList";

    @Autowired
    private AccountFacade accountFacade;

    @Autowired
    private AccountValidator accountValidator;

    @RequestMapping("/account/incomes")
    public String listIncome(final Model model, final Locale locale) {
        LOG.debug("Listing income accounts.");
        model.addAttribute(INCOME_ACCOUNTS, accountFacade.getIncomeAccounts(locale));
        return "loaded/incomes";
    }
    
    @RequestMapping("/account/expenses")
    public String listExpense(final Model model, final Locale locale) {
        LOG.debug("Listing expense accounts.");
        model.addAttribute(EXPENSE_ACCOUNTS, accountFacade.getExpenseAccounts(locale));
        return "loaded/expenses";
    }

    @RequestMapping(value = "/account/edit", method = RequestMethod.GET)
    public String editAccount(final Model model,
            @RequestParam(value = "accountId", required = false) final Integer accountId,
            @RequestParam(value = "type", required = false, defaultValue = ASSETS) final AccountVOType type) {
        LOG.debug("Editing account, id: " + accountId + ", type: " + type + ".");
        model.addAttribute(ACCOUNT_BEAN, accountFacade.getAccount(accountId, type));
        model.addAttribute(CURRENCY_LIST, accountFacade.getCurrencyOptions());
        return "loaded/account";
    }

    @RequestMapping(value = "/account/edit", method = RequestMethod.POST)
    public String saveAccount(final Model model,
            @ModelAttribute(ACCOUNT_BEAN) @Valid final AccountBean accountBean,
            final BindingResult bindingResult) {
        LOG.debug("Saving account, id: " + accountBean.getAccountId() + ".");
        accountValidator.validate(accountBean, bindingResult);
        if (bindingResult.hasErrors()) {
            ValidationErrorLoger.debug(bindingResult);
            model.addAttribute(CURRENCY_LIST, accountFacade.getCurrencyOptions());
        } else {
            accountFacade.save(accountBean);
        }
        return "loaded/account";
    }

    /*@RequestMapping(value = "/account/delete", method = RequestMethod.GET)
    public String deleteTransaction(final Model model,
            @RequestParam(value = "accountId", required = false) final Integer accountId) {
        LOG.debug("Delete account, id: " + accountId + ".");
        AccountBean accountBean = accountFacade.getAccount(accountId);
        model.addAttribute(ACCOUNT_BEAN, accountBean);
        return "loaded/deleteAccount";
    }*/

    @RequestMapping(value = "/account/delete", method = RequestMethod.POST)
    public String deleteTransaction(@ModelAttribute(ACCOUNT_BEAN) @Valid final AccountBean accountBean,
            final BindingResult bindingResult) {
        LOG.debug("Removes account, id: " + accountBean.getAccountId() + ".");
        accountFacade.delete(accountBean.getAccountId());
        return "loaded/deleteAccount";
    }

    @RequestMapping("/account/options")
    @ResponseBody
    public List<AccountOption> list(
            @RequestParam(value = "type", required = false) final AccountVOType type,
            @RequestParam(value = "currency", required = false) final Integer currency) {
        return accountFacade.getAccountOptions(type, currency);
    }
}
