package org.ua.oblik.domain.dao;

import java.math.BigDecimal;
import java.util.Map;
import javax.persistence.NoResultException;
import org.ua.oblik.domain.model.Currency;

/**
 *
 * @author Anton Bakalets
 */
public interface CurrencyDao extends DaoFacade<Integer, Currency> {

    Currency selectDefault() throws NoResultException;
    
    boolean isSymbolExists(String symbol);

    boolean isDefaultExists();
    
    Map<Integer, BigDecimal> assetsByCurrencyId();
    
}
