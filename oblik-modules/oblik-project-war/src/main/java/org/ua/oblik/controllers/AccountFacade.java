package org.ua.oblik.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.controllers.beans.AccountBean;
import org.ua.oblik.controllers.beans.AccountListBean;
import org.ua.oblik.controllers.beans.AccountOption;
import org.ua.oblik.controllers.beans.CurrencyOption;
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.beans.AccountCriteria;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.AccountVOType;
import org.ua.oblik.service.beans.CurrencyVO;

/**
 *
 */
class AccountFacade extends AbstractHelper {

    @Autowired
    private AccountService accountService;

    @Autowired
    private CurrencyService currencyService;
    
    List<AccountListBean> getIncomeAccounts(Locale locale) {
        List<AccountVO> incomeAccountsListVO = accountService.getAccounts(AccountCriteria.INCOME_CRITERIA);
        return convertList(incomeAccountsListVO, locale);
    }

    List<AccountListBean> getExpenseAccounts(Locale locale) {
        List<AccountVO> incomeAccountsListVO = accountService.getAccounts(AccountCriteria.EXPENSE_CRITERIA);
        return convertList(incomeAccountsListVO, locale);
    }

    List<AccountListBean> getAssetsAccounts(Locale locale) {
        List<AccountVO> accounts = accountService.getAccounts(AccountCriteria.ASSETS_CRITERIA);
        return convertList(accounts, locale);
    }
    
    List<CurrencyOption> getCurrencyOptions() {
        return convertOptions(currencyService.getCurrencies());
    }

    List<AccountOption> getAccountOptions(AccountVOType type, Integer currencyId) {
        final List<AccountVO> accounts = accountService.getAccounts(new AccountCriteria.Builder()
                .setType(type)
                .setCurrencyId(currencyId).build());
        return convertToOptions(accounts);
    }

    AccountBean getAccount(final Integer accountId, AccountVOType type) {
        AccountBean accountBean = convert(accountId == null ? new AccountVO() : accountService.getAccount(accountId));
        accountBean.setOldName(accountBean.getName());
        accountBean.setKind(type);
        return accountBean;
    }

    void save(AccountBean accountBean) {
        AccountVO avo = convert(accountBean);
        if (avo.getType() == null) {
            avo.setType(AccountVOType.ASSETS);
        }
        accountService.save(avo);
    }

    void delete(Integer accountId) {
        accountService.delete(accountId);
    }

    private List<CurrencyOption> convertOptions(List<CurrencyVO> currencies) {
        List<CurrencyOption> result = new ArrayList<>();
        for (CurrencyVO currency : currencies) {
            CurrencyOption option = new CurrencyOption();
            option.setCurrencyId(currency.getCurrencyId());
            option.setSymbol(currency.getSymbol());
            result.add(option);
        }
        return result;
    }

    private List<AccountListBean> convertList(List<AccountVO> list, Locale locale) {
        List<AccountListBean> result = new ArrayList<>();
        for (AccountVO temp : list) {
            result.add(convertForList(temp, locale));
        }
        return result;
    }

    private AccountListBean convertForList(AccountVO avo, Locale locale) {
        AccountListBean result = new AccountListBean();
        result.setAccountId(avo.getAccountId());
        result.setName(avo.getName());
        result.setCurrencyId(avo.getCurrencyId());
        result.setKind(avo.getType());
        result.setAmount(formatDecimal(avo.getAmmount(), locale));
        result.setCurrencySymbol(avo.getCurrencySymbol());
        return result;
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
}
