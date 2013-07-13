package org.ua.oblik.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.ua.oblik.domain.dao.CurrencyDao;
import org.ua.oblik.domain.model.Currency;
import org.ua.oblik.service.beans.CurrencyVO;

/**
 *
 * @author Anton Bakalets
 */
public class CurrencyServiceImpl implements CurrencyService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceImpl.class);
    
    @Autowired
    private CurrencyDao currencyDao;

    @Override
    @Transactional
    public void save(CurrencyVO cvo) {
        if (cvo.getCurrencyId() == null) {
            insert(cvo);
        } else {
            update(cvo);
        }
    }

    private void insert(CurrencyVO cvo) {
        LOGGER.debug("Saving new currency, symbol: " + cvo.getSymbol());
        Currency currency = new Currency();
        currency.setByDefault(false);
        currency.setRate(cvo.getRate());
        currency.setSymbol(cvo.getSymbol());
        currencyDao.insert(currency);
        cvo.setCurrencyId(currency.getId());
    }

    private void update(CurrencyVO cvo) {
        Currency currency = currencyDao.select(cvo.getCurrencyId());
        currency.setRate(cvo.getRate());
        currency.setSymbol(cvo.getSymbol());
        currencyDao.insert(currency);
    }
    
    @Override
    public List<CurrencyVO> getCurrencies() {
        return convert(currencyDao.selectAll());
    }

    @Override
    public CurrencyVO getCurrency(Integer currencyId) {
        return convert(currencyDao.select(currencyId));
    }
    
    @Override
    public CurrencyVO getDefaultCurrency() throws EntityNotFoundException {
        try {
            return convert(currencyDao.selectDefault());
        } catch (NoResultException nre) {
            throw new EntityNotFoundException("Default currency not found.");
        }
    }

    @Override
    @Transactional
    public void saveAsDefault(CurrencyVO cvo) throws RuntimeException {
        try {
            getDefaultCurrency();
            throw new RuntimeException("Default currency already defined");
        } catch (EntityNotFoundException enfe) {
            // OK - default currency not found
            LOGGER.debug("Saving default currency, symbol: " + cvo.getSymbol());
            Currency currency = new Currency();
            currency.setByDefault(true);
            currency.setRate(BigDecimal.ONE);
            currency.setSymbol(cvo.getSymbol());
            currencyDao.insert(currency);
            cvo.setCurrencyId(currency.getId());
        }
    }
    
    @Override
    public boolean isSymbolExists(String symbol) {
    	return currencyDao.isSymbolExists(symbol);
    }
    
    
    private static CurrencyVO convert(Currency model) {
        CurrencyVO result = new CurrencyVO();
        result.setCurrencyId(model.getId());
        result.setRate(model.getRate());
        result.setSymbol(model.getSymbol());
        return result;
    }
    
    private static List<CurrencyVO> convert(List<Currency> modelList) {
        List<CurrencyVO> result = new ArrayList<>(modelList.size());
        for(Currency model : modelList) {
            result.add(convert(model));
        }
        return result;
    }
}
