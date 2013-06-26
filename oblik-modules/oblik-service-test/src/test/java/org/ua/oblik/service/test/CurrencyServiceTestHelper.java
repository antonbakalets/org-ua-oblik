package org.ua.oblik.service.test;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.beans.CurrencyVO;

/**
 *
 * @author Anton Bakalets
 */
public class CurrencyServiceTestHelper {

    private Map<DefinedCurrency, CurrencyVO> currencies = new EnumMap<DefinedCurrency, CurrencyVO>(DefinedCurrency.class);
    
    @Autowired
    private CurrencyService currencyService;
    
    public CurrencyVO createAndSaveAsDefault(DefinedCurrency definedCurrency) {
        final CurrencyVO defaultCurrency = createCurrency(definedCurrency);
        currencyService.saveAsDefault(defaultCurrency); 
        return defaultCurrency;
    }
    
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
        currencies.put(DefinedCurrency.UGH, createAndSaveAsDefault(DefinedCurrency.UGH));
        currencies.put(DefinedCurrency.USD, createAndSaveCurrency(DefinedCurrency.USD));
        currencies.put(DefinedCurrency.EUR, createAndSaveCurrency(DefinedCurrency.EUR));
    }
    
    public CurrencyVO getDefinedCurrency(DefinedCurrency definedCurrency) {
        return currencies.get(definedCurrency);
    }
    
    public BigDecimal getRate(Integer currencyId) {
        return currencyService.getCurrency(currencyId).getRate();
    }
}
