package org.ua.oblik.controllers.beans;

import org.ua.oblik.service.beans.AccountVOType;

/**
 *
 * @author Anton Bakalets
 */
public class AccountListBean {

    private Integer accountId;

    private String name;

    private AccountVOType kind;

    private Integer currencyId;

    private String currencySymbol;

    private String amount;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountVOType getKind() {
        return kind;
    }

    public void setKind(AccountVOType kind) {
        this.kind = kind;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
