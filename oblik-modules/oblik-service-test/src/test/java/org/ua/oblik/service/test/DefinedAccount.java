package org.ua.oblik.service.test;

import org.ua.oblik.service.beans.AccountVOType;

/**
 *
 * @author Anton Bakalets
 */
public enum DefinedAccount {
    UGH_CASH(DefinedCurrency.UGH, "Готівка у гривні.", AccountVOType.ASSETS),
    UGH_CARD(DefinedCurrency.UGH, "Гривнева карточка.", AccountVOType.ASSETS),
    UGH_DEPOSIT(DefinedCurrency.UGH, "Гривневий депозит.", AccountVOType.ASSETS),
    USD_CASH(DefinedCurrency.USD, "Готівка у доларах.", AccountVOType.ASSETS),
    USD_CARD(DefinedCurrency.USD, "Доларова карточка.", AccountVOType.ASSETS),
    EURO_CARD(DefinedCurrency.EUR, "Карточка у євро.", AccountVOType.ASSETS),
    
    INCOME_SALARY(DefinedCurrency.UGH, "Зарплата.", AccountVOType.INCOME),
    INCOME_FOUND_USD(DefinedCurrency.USD, "Знахідка.", AccountVOType.INCOME),
    INCOME_PERCENT(DefinedCurrency.UGH, "Відсотки.", AccountVOType.INCOME),
    
    EXPENSE_MARKET(DefinedCurrency.UGH, "Базар.", AccountVOType.EXPENSE),
    EXPENSE_TRANSPORT(DefinedCurrency.UGH, "Транспорт.", AccountVOType.EXPENSE),
    EXPENSE_IN_DOLLAR(DefinedCurrency.USD, "Покупки в доларах.", AccountVOType.EXPENSE),
    EXPENSE_IN_EURO(DefinedCurrency.EUR, "Покупки в євро.", AccountVOType.EXPENSE),
    
    TO_BE_DELETED(DefinedCurrency.EUR, "Тестовий рахунок для видалення", AccountVOType.ASSETS);
    
    private final DefinedCurrency currency;
    private final String accountName;
    private final AccountVOType accountType;

    private DefinedAccount(DefinedCurrency currency, String accountName, AccountVOType accountType) {
        this.currency = currency;
        this.accountName = accountName;
        this.accountType = accountType;
    }

    public DefinedCurrency getCurrency() {
        return currency;
    }

    public String getAccountName() {
        return accountName;
    }

    public AccountVOType getAccountType() {
        return accountType;
    }
}
