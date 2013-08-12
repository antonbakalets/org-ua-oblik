package org.ua.oblik.service.test;

import java.math.BigDecimal;

/**
 *
 * @author Anton Bakalets
 */
public enum DefinedCurrency {
    UGH("грн.", BigDecimal.ONE),
    USD("дол. США", BigDecimal.valueOf(8.15)),
    EUR("євро", BigDecimal.valueOf(10.8));
    
    private String symbol;
    private BigDecimal rate;

    private DefinedCurrency(String symbol, BigDecimal rate) {
        this.symbol = symbol;
        this.rate = rate;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getRate() {
        return rate;
    }
}
