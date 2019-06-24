package org.ua.oblik.domain.model;

import java.math.BigDecimal;

public interface CurrencyTotal {
    Integer getCurrId();

    String getSymbol();

    boolean isByDefault();

    BigDecimal getRate();

    BigDecimal getTotal();
}
