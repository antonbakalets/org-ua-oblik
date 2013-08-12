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
    private BigDecimal firstAmmount;
    private Date date;
    private String note;
    private Integer secondAccount;
    private BigDecimal secondAmmount;

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
