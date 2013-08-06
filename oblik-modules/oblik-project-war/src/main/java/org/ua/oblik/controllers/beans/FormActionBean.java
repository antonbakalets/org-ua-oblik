package org.ua.oblik.controllers.beans;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.ua.oblik.service.beans.TransactionType;

public class FormActionBean {

    private Integer txId;

    @NotNull
    private Date date;
    
    private TransactionType type;

    @NotNull
    private Integer firstAccount;

    @NotNull
    private BigDecimal firstAmmount;

    @NotNull
    private Integer secondAccount;

    // is validated depending on type
    private BigDecimal secondAmmount;

    private String note;

    public Integer getTxId() {
        return txId;
    }

    public void setTxId(Integer txId) {
        this.txId = txId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Integer getFirstAccount() {
        return firstAccount;
    }

    public void setFirstAccount(Integer firstAccount) {
        this.firstAccount = firstAccount;
    }

    public BigDecimal getFirstAmmount() {
        return firstAmmount;
    }

    public void setFirstAmmount(BigDecimal firstAmmount) {
        this.firstAmmount = firstAmmount;
    }

    public Integer getSecondAccount() {
        return secondAccount;
    }

    public void setSecondAccount(Integer secondAccount) {
        this.secondAccount = secondAccount;
    }

    public BigDecimal getSecondAmmount() {
        return secondAmmount;
    }

    public void setSecondAmmount(BigDecimal secondAmmount) {
        this.secondAmmount = secondAmmount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
