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
import org.ua.oblik.domain.beans.Identifiable;

/**
 *
 * @author Anton Bakalets
 */
@Entity
@Table(name = "currency")
public class Currency implements Serializable, Identifiable<Integer> {
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
    private List<Account> accountList;

    public Currency() {
    }

    public Currency(Integer currId) {
        this.currId = currId;
    }

    public Currency(Integer currId, String symbol, boolean byDefault, BigDecimal rate) {
        this.currId = currId;
        this.symbol = symbol;
        this.byDefault = byDefault;
        this.rate = rate;
    }

    @Override
    public Integer getId() {
        return currId;
    }

    @Override
    public void setId(Integer id) {
        this.currId = id;
    }

    public Integer getCurrId() {
        return currId;
    }

    public void setCurrId(Integer currId) {
        this.currId = currId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean getByDefault() {
        return byDefault;
    }

    public void setByDefault(boolean byDefault) {
        this.byDefault = byDefault;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
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
