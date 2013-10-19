package org.ua.oblik.service;

import java.util.List;
import org.ua.oblik.service.beans.CurrencyVO;

/**
 *
 * @author Anton Bakalets
 */
public interface CurrencyService {

    CurrencyVO createCurrency();
    
    CurrencyVO getCurrency(Integer currencyId);
    
    CurrencyVO getDefaultCurrency();
    
    void save(CurrencyVO cvo);
    
    List<CurrencyVO> getCurrencies();
    
    boolean isDefaultExists();
    
    boolean isSymbolExists(String symbol);
}
