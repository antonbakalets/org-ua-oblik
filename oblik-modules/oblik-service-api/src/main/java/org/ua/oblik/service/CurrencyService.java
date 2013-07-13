package org.ua.oblik.service;

import java.util.List;
import org.ua.oblik.service.beans.CurrencyVO;

/**
 *
 * @author Anton Bakalets
 */
public interface CurrencyService {

    void save(CurrencyVO cvo);
    
    void saveAsDefault(CurrencyVO cvo);
    
    List<CurrencyVO> getCurrencies();

    CurrencyVO getCurrency(Integer currencyId);
    
    CurrencyVO getDefaultCurrency() throws EntityNotFoundException;
    
    boolean isSymbolExists (String symbol);
}
