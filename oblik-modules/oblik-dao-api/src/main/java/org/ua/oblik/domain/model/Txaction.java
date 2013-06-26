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
import org.ua.oblik.domain.beans.Identifiable;

/**
 *
 * @author Anton Bakalets
 */
@Entity
@Table(name = "txaction")
public class Txaction implements Serializable, Identifiable<Integer> {
    
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
    private Account credit;
    
    @Column(name = "credit_ammount")
    private BigDecimal creditAmmount;

    @JoinColumn(name = "debet", referencedColumnName = "acco_id")
    @ManyToOne
    private Account debet;

    @Column(name = "debet_ammount")
    private BigDecimal debetAmmount;
    
    @Column(name = "tx_comment")
    private String comment;

    public Txaction() {
    }

    public Txaction(Integer txacId) {
        this.txacId = txacId;
    }

    @Override
    public Integer getId() {
        return txacId;
    }

    @Override
    public void setId(Integer id) {
        this.txacId = id;
    }

    public Integer getTxacId() {
        return txacId;
    }

    public void setTxacId(Integer txacId) {
        this.txacId = txacId;
    }

    public Date getTxDate() {
        return txDate;
    }

    public void setTxDate(Date txDate) {
        this.txDate = txDate;
    }

    public Account getCredit() {
        return credit;
    }

    public void setCredit(Account credit) {
        this.credit = credit;
    }

    public Account getDebet() {
        return debet;
    }

    public void setDebet(Account debet) {
        this.debet = debet;
    }

    public BigDecimal getCreditAmmount() {
        return creditAmmount;
    }

    public void setCreditAmmount(BigDecimal creditAmmount) {
        this.creditAmmount = creditAmmount;
    }

    public BigDecimal getDebetAmmount() {
        return debetAmmount;
    }

    public void setDebetAmmount(BigDecimal debetAmmount) {
        this.debetAmmount = debetAmmount;
    }

    public String getComment() {
        return comment;
    }

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
