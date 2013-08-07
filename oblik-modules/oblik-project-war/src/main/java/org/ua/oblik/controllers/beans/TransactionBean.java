package org.ua.oblik.controllers.beans;

import java.math.BigDecimal;
import java.util.Date;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.TransactionType;

public class TransactionBean {

    private Integer transactionId;

    private TransactionType type;

    private AccountVO firstAccount;

    private BigDecimal firstAmmount;

    private Date date;

    private String note;

    private AccountVO secondAccount;

    private BigDecimal secondAmmount;

    

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

    public AccountVO getFirstAccount() {
        return firstAccount;
    }

    public void setFirstAccount(AccountVO firstAccount) {
        this.firstAccount = firstAccount;
    }

    public BigDecimal getFirstAmmount() {
        return firstAmmount;
    }

    public void setFirstAmmount(BigDecimal firstAmmount) {
        this.firstAmmount = firstAmmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public AccountVO getSecondAccount() {
        return secondAccount;
    }

    public void setSecondAccount(AccountVO secondAccount) {
        this.secondAccount = secondAccount;
    }

    public BigDecimal getSecondAmmount() {
        return secondAmmount;
    }

    public void setSecondAmmount(BigDecimal secondAmmount) {
        this.secondAmmount = secondAmmount;
    }
}