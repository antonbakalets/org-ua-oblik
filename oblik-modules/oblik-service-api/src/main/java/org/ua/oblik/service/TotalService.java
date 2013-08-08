package org.ua.oblik.service;

import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author Anton Bakalets
 */
public interface TotalService {

    BigDecimal getDefaultCurrencyTotal();

    BigDecimal getCurrencyTotal(Integer currencyId);
    
    Map<Integer, BigDecimal> getCurrenciesTotal();
    
}
