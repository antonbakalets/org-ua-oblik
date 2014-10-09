package org.ua.oblik.domain.dao;

import java.math.BigDecimal;
import java.util.Map;

import org.ua.oblik.domain.model.CurrencyEntity;

/**
 *
 * @author Anton Bakalets
 */
public interface CurrencyDao extends DaoFacade<Integer, CurrencyEntity> {

    CurrencyEntity selectDefault();
    
    boolean isSymbolExists(String symbol);

    boolean isDefaultExists();
    
    Map<Integer, BigDecimal> assetsByCurrencyId();
    
}
