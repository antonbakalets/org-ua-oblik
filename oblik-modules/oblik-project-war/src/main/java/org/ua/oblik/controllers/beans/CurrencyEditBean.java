package org.ua.oblik.controllers.beans;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Anton Bakalets
 */
public class CurrencyEditBean {

    private static final int SYMBOL_MAX_LENGHT = 10;
    private static final String RATE_MIN_VALUE = "0.001";

    private Integer currencyId;

    @NotNull
    @DecimalMin(value = RATE_MIN_VALUE)
    private BigDecimal rate;

    @NotNull
    @Size(min = 1, max = SYMBOL_MAX_LENGHT)
    private String symbol;

    private String oldSymbol;

    private Boolean defaultRate;

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

    public Boolean getDefaultRate() {
        return defaultRate;
    }

    public void setDefaultRate(Boolean defaultRate) {
        this.defaultRate = defaultRate;
    }

    public String getOldSymbol() {
        return oldSymbol;
    }

    public void setOldSymbol(String oldSymbol) {
        this.oldSymbol = oldSymbol;
    }
}
