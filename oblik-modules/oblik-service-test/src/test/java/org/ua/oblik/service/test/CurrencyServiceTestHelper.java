package org.ua.oblik.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.beans.CurrencyVO;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author Anton Bakalets
 */
public class CurrencyServiceTestHelper {

    private Map<DefinedCurrency, CurrencyVO> currencies = new EnumMap<>(DefinedCurrency.class);
    
    @Autowired
    private CurrencyService currencyService;
        
    private CurrencyVO createCurrency(DefinedCurrency definedCurrency) {
        CurrencyVO cvo = new CurrencyVO();
        cvo.setSymbol(definedCurrency.getSymbol());
        cvo.setRate(definedCurrency.getRate());
        return cvo;
    }
    
    public CurrencyVO createAndSaveCurrency(DefinedCurrency definedCurrency) {
        final CurrencyVO created = createCurrency(definedCurrency);
        currencyService.save(created);
        return created;
    }

    public void saveDefinedCurrencies() {
        for (DefinedCurrency dc : DefinedCurrency.values()) {
            currencies.put(dc, createAndSaveCurrency(dc));
        }
    }
    
    public CurrencyVO getDefinedCurrency(DefinedCurrency definedCurrency) {
        return currencies.get(definedCurrency);
    }
    
    public Integer currencyId(DefinedCurrency definedCurrency) {
        return currencies.get(definedCurrency).getCurrencyId();
    }
    
    public BigDecimal getRate(Integer currencyId) {
        return currencyService.getCurrency(currencyId).getRate();
    }
}
