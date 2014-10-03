package org.ua.oblik.controllers;

import org.ua.oblik.controllers.beans.AccountOption;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.ua.oblik.controllers.beans.AccountBean;
import org.ua.oblik.controllers.utils.ValidationErrorLoger;
import org.ua.oblik.controllers.validators.AccountValidator;
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.beans.AccountCriteria;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.AccountVOType;
import org.ua.oblik.service.beans.TransactionType;

/**
 *
 * @author Anton Bakalets
 */
@Controller
public class AccountController {

    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    private static final String ASSETS = "ASSETS";

    private static final String ASSETS_ACCOUNTS = "assetsAccounts";

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

    @ModelAttribute
    public void populateModel(final Model model) {
        model.addAttribute(CURRENCY_LIST, currencyService.getCurrencies());
    }

    @RequestMapping("/account/incomes")
    public String listIncome(final Model model) {
        LOG.debug("Listing income accounts.");
        List<AccountVO> incomeAccountsListVO = accountService.getAccounts(AccountCriteria.INCOME_CRITERIA);
        List<AccountBean> incomeAccountsList = convertList(incomeAccountsListVO);
        model.addAttribute(INCOME_ACCOUNTS, incomeAccountsList);
        return "loaded/incomes";
    }
    
    @RequestMapping("/account/expenses")
    public String listExpense(final Model model) {
        LOG.debug("Listing expense accounts.");
        List<AccountVO> expenseAccountsListVO = accountService.getAccounts(AccountCriteria.EXPENSE_CRITERIA);
        List<AccountBean> expenseAccountsList = convertList(expenseAccountsListVO);
        model.addAttribute(EXPENSE_ACCOUNTS, expenseAccountsList);
        return "loaded/expenses";
    }

    @RequestMapping(value = "/account/edit", method = RequestMethod.GET)
    public String editAccount(final Model model,
            @RequestParam(value = "accountId", required = false) final Integer accountId,
            @RequestParam(value = "type", required = false, defaultValue = ASSETS) final AccountVOType type) {
        LOG.debug("Editing account, id: " + accountId + ", type: " + type + ".");
        AccountBean accountBean = createAccount(accountId, type);
        model.addAttribute(ACCOUNT_BEAN, accountBean);
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
        } else {
            AccountVO avo = convert(accountBean);
            if (avo.getType() == null) {
                avo.setType(AccountVOType.ASSETS);
            }
            accountService.save(avo);
        }
        return "loaded/account";
    }

    @RequestMapping(value = "/account/delete", method = RequestMethod.GET)
    public String deleteTransaction(final Model model,
            @RequestParam(value = "accountId", required = false) final Integer accountId) {
        LOG.debug("Delete account, id: " + accountId + ".");
        AccountVO avo = accountService.getAccount(accountId);
        AccountBean accountBean = convert(avo);
        model.addAttribute(ACCOUNT_BEAN, accountBean);
        return "loaded/deleteAccount";
    }

    @RequestMapping(value = "/account/delete", method = RequestMethod.POST)
    public String deleteTransaction(final Model model,
            @ModelAttribute(ACCOUNT_BEAN) @Valid final AccountBean accountBean,
            final BindingResult bindingResult) {
        LOG.debug("Removes account, id: " + accountBean.getAccountId() + ".");
        accountService.delete(accountBean.getAccountId());
        return "loaded/deleteAccount";
    }
    
    @RequestMapping("/account/options")
    @ResponseBody
    public List<AccountOption> list(final Model model,
            @RequestParam(value = "type", required = false) final AccountVOType type,
            @RequestParam(value = "currency", required = false) final Integer currency) {
        final List<AccountVO> accounts = accountService.getAccounts(new AccountCriteria.Builder()
                .setType(type)
                .setCurrencyId(currency).build());
        final List<AccountOption> convertToOptions = convertToOptions(accounts);
        return convertToOptions;
    }

    private AccountVO convert(AccountBean accountBean) {
        AccountVO result = new AccountVO();
        result.setAccountId(accountBean.getAccountId());
        result.setName(accountBean.getName());
        result.setType(accountBean.getKind());
        result.setCurrencyId(accountBean.getCurrencyId());
        result.setCurrencySymbol(accountBean.getCurrencySymbol());
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
        result.setCurrencySymbol(avo.getCurrencySymbol());
        result.setRemovable(avo.isRemovable());
        return result;
    }

    private List<AccountBean> convertList(List<AccountVO> list) {
        List<AccountBean> result = new ArrayList<>();
        for (AccountVO temp : list) {
            result.add(convert(temp));
        }
        return result;
    }

    private AccountBean createAccount(final Integer accountId, AccountVOType type) {
        AccountBean accountBean = convert(accountId == null ? new AccountVO() : accountService.getAccount(accountId));
        accountBean.setOldName(accountBean.getName());
        accountBean.setKind(type);
        return accountBean;
    }
    
    private List<AccountOption> convertToOptions(List<AccountVO> accounts) {
        List<AccountOption> result = new ArrayList<>();
        for (AccountVO temp : accounts) {
            AccountOption option = new AccountOption();
            option.setId(temp.getAccountId());
            option.setName(temp.getName());
            option.setCurrency(temp.getCurrencyId());
            result.add(option);
        }
        return result;
    }
}
