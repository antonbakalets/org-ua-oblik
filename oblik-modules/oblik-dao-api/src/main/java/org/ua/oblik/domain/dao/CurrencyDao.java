package org.ua.oblik.domain.dao;

import javax.persistence.NoResultException;
import org.ua.oblik.domain.model.Currency;

/**
 *
 * @author Anton Bakalets
 */
public interface CurrencyDao extends DaoFacade<Integer, Currency> {

    Currency selectDefault() throws NoResultException;
    
    boolean isSymbolExists(String symbol);
    
}
