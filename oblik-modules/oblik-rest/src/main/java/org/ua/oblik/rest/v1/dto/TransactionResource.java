package org.ua.oblik.rest.v1.dto;

import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionResource extends ResourceSupport {

    private String type;
    private Date date;
    private Integer firstAccount;
    private BigDecimal firstAmount;
    private Integer secondAccount;
    private BigDecimal secondAmount;
    private String note;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getFirstAccount() {
        return firstAccount;
    }

    public void setFirstAccount(Integer firstAccount) {
        this.firstAccount = firstAccount;
    }

    public BigDecimal getFirstAmount() {
        return firstAmount;
    }

    public void setFirstAmount(BigDecimal firstAmount) {
        this.firstAmount = firstAmount;
    }

    public Integer getSecondAccount() {
        return secondAccount;
    }

    public void setSecondAccount(Integer secondAccount) {
        this.secondAccount = secondAccount;
    }

    public BigDecimal getSecondAmount() {
        return secondAmount;
    }

    public void setSecondAmount(BigDecimal secondAmount) {
        this.secondAmount = secondAmount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
