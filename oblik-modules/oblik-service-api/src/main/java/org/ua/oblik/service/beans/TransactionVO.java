package org.ua.oblik.service.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Anton Bakalets
 */
public class TransactionVO {
    
    private Integer txId;
    private TransactionType type;
    private Integer firstAccount;
    private BigDecimal firstAmount;
    private Date date;
    private String note;
    private Integer secondAccount;
    private BigDecimal secondAmount;

    public Integer getTxId() {
        return txId;
    }

    public void setTxId(Integer txId) {
        this.txId = txId;
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

    public BigDecimal getFirstAmount() {
        return firstAmount;
    }

    public void setFirstAmount(BigDecimal firstAmount) {
        this.firstAmount = firstAmount;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (txId != null ? txId.hashCode() : 0);
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
        final TransactionVO other = (TransactionVO) obj;
        if (!Objects.equals(this.txId, other.txId)) {
            return false;
        }
        return true;
    }
}
