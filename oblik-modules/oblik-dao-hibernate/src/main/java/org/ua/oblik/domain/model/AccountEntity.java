package org.ua.oblik.domain.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

import org.ua.oblik.domain.beans.AccountKind;

/**
 *
 * @author Anton Bakalets
 */
@Entity
@Table(name = "account")
public class AccountEntity implements Serializable, Account {

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
    @ManyToOne(optional = false, targetEntity = CurrencyEntity.class)
    private Currency currency;

    public AccountEntity() {
    }

    @Override
    public Integer getId() {
        return accoId;
    }

    @Override
    public void setId(Integer id) {
        this.accoId = id;
    }

    @Override
    public String getShortName() {
        return shortName;
    }

    @Override
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public AccountKind getKind() {
        return kind;
    }

    @Override
    public void setKind(AccountKind kind) {
        this.kind = kind;
    }

    @Override
    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
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
        AccountEntity other = (AccountEntity) object;
        if ((this.accoId == null && other.accoId != null) || (this.accoId != null && !this.accoId.equals(other.accoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AccountEntity[ accoId=" + accoId + " ]";
    }

}
