package org.ua.oblik.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

/**
 *
 * @author Anton Bakalets
 */
@Entity
@Table(name = "currency")
public class CurrencyEntity implements Serializable, Currency {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "curr_id")
    private Integer currId;

    @Basic(optional = false)
    @Column(name = "symbol")
    private String symbol;

    @Basic(optional = false)
    @Column(name = "by_default")
    private boolean byDefault;

    @Basic(optional = false)
    @Column(name = "rate")
    private BigDecimal rate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currency",
            fetch = FetchType.LAZY, targetEntity = AccountEntity.class)
    private List<Account> accounts;

    @Override
    public Integer getId() {
        return currId;
    }

    @Override
    public void setId(Integer id) {
        this.currId = id;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean isByDefault() {
        return byDefault;
    }

    @Override
    public void setByDefault(boolean byDefault) {
        this.byDefault = byDefault;
    }

    @Override
    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public List<Account> getAccounts() {
        return accounts;
    }

    @Override
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (currId != null ? currId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Currency)) {
            return false;
        }
        CurrencyEntity other = (CurrencyEntity) object;
        return Objects.equals(this.currId, other.currId);
    }

    @Override
    public String toString() {
        return "CurrencyEntity[ currId=" + currId + " ]";
    }

}
