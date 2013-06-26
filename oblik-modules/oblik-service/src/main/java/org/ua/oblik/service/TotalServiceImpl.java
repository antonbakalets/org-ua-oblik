package org.ua.oblik.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.domain.dao.AccountDao;
import org.ua.oblik.domain.dao.CurrencyDao;
import org.ua.oblik.domain.model.Currency;

/**
 *
 * @author Anton Bakalets
 */
public class TotalServiceImpl implements TotalService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TotalServiceImpl.class);

    // TODO private Map<Integer, BigDecimal> currencyTotal = Collections.emptyMap();
    
    @Autowired
    private AccountDao accountDao;
    
    @Autowired
    private CurrencyDao currencyDao;
    
    public void init() {
        
    }
    
    @Override
    public BigDecimal getDefaultCurrencyTotal() {
        // TODO move to DAO level
        BigDecimal result = BigDecimal.ZERO;
        for (Map.Entry<Integer, BigDecimal> entry : getCurrenciesTotal().entrySet()) {
            BigDecimal rate = currencyDao.select(entry.getKey()).getRate();
            result = result.add(entry.getValue().multiply(rate));
        }
        LOGGER.debug("[TOTAL] " + result);
        return result;
    }

    @Override
    public Map<Integer, BigDecimal> getCurrenciesTotal() {
        final List<Currency> currencies = currencyDao.selectAll();
        final Map<Integer, BigDecimal> currencyTotal = new HashMap<Integer, BigDecimal>(currencies.size());
        for (Currency currency : currencies) {
            currencyTotal.put(currency.getId(), accountDao.calculateTotal(currency));
        }
        return currencyTotal;
    }

    @Override
    public BigDecimal getCurrencyTotal(Integer currencyId) {
        final Currency currency = currencyDao.select(currencyId);
        return accountDao.calculateTotal(currency);
    }
}
