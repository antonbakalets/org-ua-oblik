package org.ua.oblik.service.beans;

import java.math.BigDecimal;
import java.util.Objects;

public class CurrencyVO {

    private Integer currencyId;
    private BigDecimal rate;
    private String symbol;
    private boolean defaultRate;
    private BigDecimal total;
    private boolean removable;

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean getDefaultRate() {
        return defaultRate;
    }

    public void setDefaultRate(boolean defaultRate) {
        this.defaultRate = defaultRate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CurrencyVO that = (CurrencyVO) o;
        return Objects.equals(this.currencyId, that.currencyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.currencyId);
    }

    @Override
    public String toString() {
        return "CurrencyVO{" + "currencyId=" + currencyId + ", rate=" + rate + ", symbol=" + symbol + '}';
    }
}
