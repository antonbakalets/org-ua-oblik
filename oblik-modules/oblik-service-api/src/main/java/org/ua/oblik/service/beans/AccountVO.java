package org.ua.oblik.service.beans;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Anton Bakalets
 */
public class AccountVO {

    private Integer accountId;
    private String name;
    private Integer currencyId;
    private String currencySymbol;
    private BigDecimal amount;
    private AccountVOType type;
    private boolean removable;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public AccountVOType getType() {
        return type;
    }

    public void setType(AccountVOType type) {
        this.type = type;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.accountId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AccountVO other = (AccountVO) obj;
        return Objects.equals(this.accountId, other.accountId);
    }

    @Override
    public String toString() {
        return "AccountVO{" + "accountId=" + accountId + ", name=" + name + ", currencyId=" + currencyId + ", currencySymbol=" + currencySymbol + ", amount=" + amount + ", type=" + type + '}';
    }
}
