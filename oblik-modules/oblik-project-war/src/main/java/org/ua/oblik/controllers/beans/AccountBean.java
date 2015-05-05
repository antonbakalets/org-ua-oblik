package org.ua.oblik.controllers.beans;

import java.math.BigDecimal;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.ua.oblik.service.beans.AccountVOType;

/**
 *
 * @author Ihor Senkiv
 */
public class AccountBean {

    private static final int NAME_MAX_SYMBOLS = 100;

    private Integer accountId;

    @NotNull
    @Size(min = 1, max = NAME_MAX_SYMBOLS)
    private String newName;

    private String oldName;

    @Enumerated(EnumType.STRING)
    private AccountVOType kind;

    @NotNull
    private Integer currencyId;

    private String currencySymbol;

    private BigDecimal amount;

    private boolean removable;

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }
}
