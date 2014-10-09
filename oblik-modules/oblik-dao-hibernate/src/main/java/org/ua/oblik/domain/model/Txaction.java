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
public class Txaction implements Serializable, TxactionEntity {

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
    @ManyToOne
    private AccountEntity credit;

    @Column(name = "credit_ammount")
    private BigDecimal creditAmmount;

    @JoinColumn(name = "debet", referencedColumnName = "acco_id")
    @ManyToOne
    private AccountEntity debet;

    @Column(name = "debet_ammount")
    private BigDecimal debetAmmount;

    @Column(name = "tx_comment")
    private String comment;

    public Txaction() {
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
    public AccountEntity getCredit() {
        return credit;
    }

    @Override
    public void setCredit(AccountEntity credit) {
        this.credit = credit;
    }

    @Override
    public AccountEntity getDebet() {
        return debet;
    }

    @Override
    public void setDebet(AccountEntity debet) {
        this.debet = debet;
    }

    @Override
    public BigDecimal getCreditAmmount() {
        return creditAmmount;
    }

    @Override
    public void setCreditAmmount(BigDecimal creditAmmount) {
        this.creditAmmount = creditAmmount;
    }

    @Override
    public BigDecimal getDebetAmmount() {
        return debetAmmount;
    }

    @Override
    public void setDebetAmmount(BigDecimal debetAmmount) {
        this.debetAmmount = debetAmmount;
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
        if (!(object instanceof TxactionEntity)) {
            return false;
        }
        Txaction other = (Txaction) object;
        if ((this.txacId == null && other.txacId != null) || (this.txacId != null && !this.txacId.equals(other.txacId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ua.oblik.domain.model.Txaction[ txacId=" + txacId + " ]";
    }

}
