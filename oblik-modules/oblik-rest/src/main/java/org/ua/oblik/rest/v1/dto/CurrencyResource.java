package org.ua.oblik.rest.v1.dto;

import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;

public class CurrencyResource extends ResourceSupport {

    private Integer currencyId;
    private boolean defaultRate;
    private String symbol;
    private BigDecimal rate;
    private BigDecimal total;

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setDefaultRate(boolean defaultRate) {
        this.defaultRate = defaultRate;
    }

    public boolean isDefaultRate() {
        return defaultRate;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
