package org.ua.oblik.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.domain.dao.AccountDao;
import org.ua.oblik.domain.dao.CurrencyDao;
import org.ua.oblik.domain.model.Currency;

import java.math.BigDecimal;

/**
 *
 * @author Anton Bakalets
 */
public class TotalServiceImpl implements TotalService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TotalServiceImpl.class);

    @Autowired
    private AccountDao accountDao;
    
    @Autowired
    private CurrencyDao currencyDao;
        
    @Override
    public BigDecimal getDefaultCurrencyTotal() {
        final BigDecimal defaultTotal = accountDao.calculateDefaultTotal();
        LOGGER.debug("[TOTAL] Default currency total: {}", defaultTotal);
        return defaultTotal;
    }

    @Override
    public BigDecimal getCurrencyTotal(Integer currencyId) {
        final Currency currency = currencyDao.getOne(currencyId);
        return accountDao.calculateTotal(currency);
    }
}
