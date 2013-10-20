package org.ua.oblik.domain.model;

import org.ua.oblik.domain.beans.AccountKind;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.ua.oblik.domain.beans.Identifiable;

/**
 *
 * @author Anton Bakalets
 */
@Entity
@Table(name = "account")
public class Account implements Serializable, Identifiable<Integer> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "acco_id")
    private Integer accoId;

    @Basic(optional = false)
    @Column(name = "short_name")
    private String shortName;

    @Basic(optional = false)
    @Column(name = "kind")
    @Enumerated(EnumType.STRING)
    private AccountKind kind;

    @Basic(optional = false)
    @Column(name = "total")
    private BigDecimal total;

    @JoinColumn(name = "currency", referencedColumnName = "curr_id")
    @ManyToOne(optional = false)
    private Currency currency;

    public Account() {
    }

    @Override
    public Integer getId() {
        return accoId;
    }

    @Override
    public void setId(Integer id) {
        this.accoId = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public AccountKind getKind() {
        return kind;
    }

    public void setKind(AccountKind kind) {
        this.kind = kind;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accoId != null ? accoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.accoId == null && other.accoId != null) || (this.accoId != null && !this.accoId.equals(other.accoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Account[ accoId=" + accoId + " ]";
    }

}
