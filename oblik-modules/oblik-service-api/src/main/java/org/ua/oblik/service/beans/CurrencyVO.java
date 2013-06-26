package org.ua.oblik.service.beans;

import java.math.BigDecimal;

/**
 *
 * @author Anton Bakalets
 */
public class CurrencyVO {

    private Integer currencyId;
    private BigDecimal rate;
    private String symbol;

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

    @Override
    public String toString() {
        return "CurrencyVO{" + "currencyId=" + currencyId + ", rate=" + rate + ", symbol=" + symbol + '}';
    }
}
