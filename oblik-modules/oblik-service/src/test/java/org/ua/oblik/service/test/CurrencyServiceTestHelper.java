package org.ua.oblik.service.test;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ua.oblik.service.AccountServiceTest;
import org.ua.oblik.service.BusinessConstraintException;
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.beans.CurrencyVO;

/**
 *
 * @author Anton Bakalets
 */
@Component
public class CurrencyServiceTestHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceTestHelper.class);

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
        try {
            currencyService.save(created);
        } catch (NotFoundException e) {
            LOGGER.debug("Shouldn't.", e);
        } catch (BusinessConstraintException e) {
            LOGGER.debug("Shouldn't.", e);
         }
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
