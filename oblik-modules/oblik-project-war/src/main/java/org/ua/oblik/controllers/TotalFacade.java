package org.ua.oblik.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.service.AccountService;
import org.ua.oblik.service.CurrencyService;

/**
 *
 */
public class TotalFacade extends AbstractHelper {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private AccountService accountService;

    public boolean isDefaultExists() {
        return currencyService.isDefaultExists();
    }

    public String getDefaultCurrencySymbol() {
        return currencyService.getDefaultCurrency().getSymbol();
    }

    public String totalAssets(Locale locale) {
        return formatDecimal(accountService.totalAssets(), locale);
    }
}
