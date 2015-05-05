package org.ua.oblik.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Anton Bakalets
 */
@Entity
@Table(name = "txaction")
public class TxactionEntity implements Serializable, Txaction {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "txac_id")
    private Integer txacId;

    @Basic(optional = false)
    @Column(name = "tx_date")
    @Temporal(TemporalType.DATE)
    private Date txDate;

    @JoinColumn(name = "credit", referencedColumnName = "acco_id")
    @ManyToOne(targetEntity = AccountEntity.class)
    private Account credit;

    @Column(name = "credit_amount")
    private BigDecimal creditAmount;

    @JoinColumn(name = "debet", referencedColumnName = "acco_id")
    @ManyToOne(targetEntity = AccountEntity.class)
    private Account debet;

    @Column(name = "debet_amount")
    private BigDecimal debetAmount;

    @Column(name = "tx_comment")
    private String comment;

    public TxactionEntity() {
    }

    @Override
    public Integer getId() {
        return txacId;
    }

    @Override
    public void setId(Integer id) {
        this.txacId = id;
    }

    @Override
    public Date getTxDate() {
        return txDate;
    }

    @Override
    public void setTxDate(Date txDate) {
        this.txDate = txDate;
    }

    @Override
    public Account getCredit() {
        return credit;
    }

    @Override
    public void setCredit(Account credit) {
        this.credit = credit;
    }

    @Override
    public Account getDebet() {
        return debet;
    }

    @Override
    public void setDebet(Account debet) {
        this.debet = debet;
    }

    @Override
    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    @Override
    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    @Override
    public BigDecimal getDebetAmount() {
        return debetAmount;
    }

    @Override
    public void setDebetAmount(BigDecimal debetAmount) {
        this.debetAmount = debetAmount;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (txacId != null ? txacId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Txaction)) {
            return false;
        }
        TxactionEntity other = (TxactionEntity) object;
        if ((this.txacId == null && other.txacId != null) || (this.txacId != null && !this.txacId.equals(other.txacId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TxactionEntity[ txacId=" + txacId + " ]";
    }

}
