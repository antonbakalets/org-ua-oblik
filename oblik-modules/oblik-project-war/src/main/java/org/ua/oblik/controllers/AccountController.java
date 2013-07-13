package org.ua.oblik.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.ua.oblik.controllers.beans.AccountBean;
import org.ua.oblik.controllers.utils.ValidationErrorLoger;
import org.ua.oblik.controllers.validators.AccountValidator;
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.AccountVOType;


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
    
    private static final String ACCOUNT_BEAN = "accountBean";
    
    private static final String CURRENCY_LIST = "currencyList";
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private CurrencyService currencyService;
    
    @Autowired
    private AccountValidator accountValidator;
    
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
    
    @RequestMapping(value = "/account/edit", method = RequestMethod.GET)
    public String editAccount(final Model model,
            final @RequestParam(value = "accountId", required = false) Integer accountId) {
        LOG.debug("Editing account, id: " + accountId + ".");
        AccountVO account = accountId == null 
                ? new AccountVO()
                : accountService.getAccount(accountId);
        AccountBean accountBean = convert(account);
        model.addAttribute(CURRENCY_LIST, currencyService.getCurrencies());
        model.addAttribute(ACCOUNT_BEAN, accountBean);
        return "loaded/account";
    }

    @RequestMapping(value = "/account/edit", method = RequestMethod.POST)
    public String saveAccount(final Model model, final @ModelAttribute(ACCOUNT_BEAN) @Valid AccountBean accountBean,
            BindingResult bindingResult) {
        LOG.debug("Saving account, id: " + accountBean.getAccountId() + ".");
        accountValidator.validate(accountBean, bindingResult);
        if (bindingResult.hasErrors()) {
            ValidationErrorLoger.debug(bindingResult);
        } else {
        	AccountVO avo = convert(accountBean);
	        if(avo.getType()==null) {
	            avo.setType(AccountVOType.ASSETS);
	        }
	        accountService.save(avo);
        }
        return "loaded/account";
    }
    
    
    private AccountVO convert(AccountBean accountBean) {
        AccountVO result = new AccountVO();
        result.setAccountId(accountBean.getAccountId());
        result.setName(accountBean.getName());
        result.setType(accountBean.getKind());
        result.setCurrencyId(accountBean.getCurrencyId());
        result.setCurrencySymbol(currencyService.getCurrency(accountBean.getCurrencyId()).getSymbol());
        result.setAmmount(accountBean.getAmmount());
        return result;
    }

    private AccountBean convert(AccountVO avo) {
        AccountBean result = new AccountBean();
        result.setAccountId(avo.getAccountId());
        result.setName(avo.getName());
        result.setCurrencyId(avo.getCurrencyId());
        result.setKind(avo.getType());
        result.setAmmount(avo.getAmmount());
        return result;
    }
}
