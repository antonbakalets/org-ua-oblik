package org.ua.oblik.service.beans;

import java.math.BigDecimal;

/**
 *
 * @author Anton Bakalets
 */
public class AccountVO {

    private Integer accountId;
    private String name;
    private Integer currencyId;
    private String currencySymbol;
    private BigDecimal ammount;
    private AccountVOType type;

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

    public BigDecimal getAmmount() {
        return ammount;
    }

    public void setAmmount(BigDecimal ammount) {
        this.ammount = ammount;
    }

    public AccountVOType getType() {
        return type;
    }

    public void setType(AccountVOType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AccountVO{" + "accountId=" + accountId + ", name=" + name + ", currencyId=" + currencyId + ", currencySymbol=" + currencySymbol + ", ammount=" + ammount + ", type=" + type + '}';
    }
}
