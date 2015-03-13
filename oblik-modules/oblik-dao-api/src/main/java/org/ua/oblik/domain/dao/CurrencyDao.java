package org.ua.oblik.domain.dao;

import java.math.BigDecimal;
import java.util.Map;

import org.ua.oblik.domain.model.Currency;

/**
 *
 * @author Anton Bakalets
 */
public interface CurrencyDao extends DaoFacade<Integer, Currency> {

    Currency selectDefault();
    
    boolean isSymbolExists(String symbol);

    boolean isDefaultExists();
    
    Map<Integer, BigDecimal> assetsByCurrencyId();

    boolean isUsed(Integer currencyId);
}
