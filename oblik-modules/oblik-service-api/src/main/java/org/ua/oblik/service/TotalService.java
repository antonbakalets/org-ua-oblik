package org.ua.oblik.service;

import java.math.BigDecimal;

/**
 *
 * @author Anton Bakalets
 */
public interface TotalService {

    BigDecimal getDefaultCurrencyTotal();

    BigDecimal getCurrencyTotal(Integer currencyId);
    
}
