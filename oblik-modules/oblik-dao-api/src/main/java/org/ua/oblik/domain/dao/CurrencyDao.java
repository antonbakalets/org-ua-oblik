package org.ua.oblik.domain.dao;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ua.oblik.domain.model.Currency;

/**
 *
 * @author Anton Bakalets
 */
public interface CurrencyDao extends JpaRepository<Currency, Integer>, CurrencyRepositoryFragment {

    Currency selectDefault();
    
    boolean isSymbolExists(String symbol);

    boolean isDefaultExists();
    
    Map<Integer, BigDecimal> assetsByCurrencyId();

    boolean isUsed(Integer currencyId);

    long count();
}
