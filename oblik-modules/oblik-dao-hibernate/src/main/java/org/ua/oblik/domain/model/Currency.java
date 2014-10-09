package org.ua.oblik.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Anton Bakalets
 */
@Entity
@Table(name = "currency")
public class Currency implements Serializable, CurrencyEntity {

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currency", fetch = FetchType.LAZY)
    private List<AccountEntity> accounts;

    public Currency() {
    }

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
    public boolean getByDefault() {
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
    public List<AccountEntity> getAccounts() {
        return accounts;
    }

    @Override
    public void setAccounts(List<AccountEntity> accounts) {
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
        if (!(object instanceof CurrencyEntity)) {
            return false;
        }
        Currency other = (Currency) object;
        if ((this.currId == null && other.currId != null) || (this.currId != null && !this.currId.equals(other.currId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Currency[ currId=" + currId + " ]";
    }

}
