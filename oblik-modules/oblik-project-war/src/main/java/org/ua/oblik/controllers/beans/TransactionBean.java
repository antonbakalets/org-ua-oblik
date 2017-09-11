package org.ua.oblik.controllers.beans;

import org.ua.oblik.service.beans.TransactionType;

public class TransactionBean {

    private Integer transactionId;

    private TransactionType type;

    private String firstAccountName;

    private String firstAmount;
    
    private String firstSymbol;

    private String date;

    private String note;

    private String secondAccountName;

    private String secondAmount;
    
    private String secondSymbol;

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getFirstAccountName() {
        return firstAccountName;
    }

    public void setFirstAccountName(String firstAccountName) {
        this.firstAccountName = firstAccountName;
    }

    public String getFirstAmount() {
        return firstAmount;
    }

    public void setFirstAmount(String firstAmount) {
        this.firstAmount = firstAmount;
    }

    public String getFirstSymbol() {
        return firstSymbol;
    }

    public void setFirstSymbol(String firstSymbol) {
        this.firstSymbol = firstSymbol;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSecondAccountName() {
        return secondAccountName;
    }

    public void setSecondAccountName(String secondAccountName) {
        this.secondAccountName = secondAccountName;
    }

    public String getSecondAmount() {
        return secondAmount;
    }

    public void setSecondAmount(String secondAmount) {
        this.secondAmount = secondAmount;
    }

    public String getSecondSymbol() {
        return secondSymbol;
    }

    public void setSecondSymbol(String secondSymbol) {
        this.secondSymbol = secondSymbol;
    }
}
