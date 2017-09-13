package org.ua.oblik.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.ua.oblik.domain.dao.CurrencyDao;
import org.ua.oblik.domain.model.Currency;
import org.ua.oblik.domain.model.EntitiesFactory;
import org.ua.oblik.service.beans.CurrencyVO;

/**
 *
 * @author Anton Bakalets
 */
public class CurrencyServiceImpl implements CurrencyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    private CurrencyDao currencyDao;
    private TotalService totalService;
    private EntitiesFactory entitiesFactory;

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
        Currency currency = entitiesFactory.createCurrencyEntity();
        currency.setSymbol(cvo.getSymbol());
        if (isDefaultExists()) {
            LOGGER.debug("Saving new currency, symbol: {}.",cvo.getSymbol());
            currency.setByDefault(false);
            currency.setRate(cvo.getRate());
        } else {
            LOGGER.debug("Saving default currency, symbol: {}.", cvo.getSymbol());
            currency.setByDefault(true);
            currency.setRate(BigDecimal.ONE);
        }
        currencyDao.insert(currency);
        cvo.setCurrencyId(currency.getId());
    }

    private void update(CurrencyVO cvo) {
        LOGGER.debug("Updating currency, symbol: {}.", cvo.getSymbol());
        Currency currency = currencyDao.select(cvo.getCurrencyId());
        currency.setRate(cvo.getRate());
        currency.setSymbol(cvo.getSymbol());
    }

    @Override
    public List<CurrencyVO> getCurrencies() {
        final List<? extends Currency> currencies = currencyDao.selectAll();
        final Map<Integer, BigDecimal> assetsByCurrency = totalService.getCurrenciesTotal();
        List<CurrencyVO> result = new ArrayList<>(currencies.size());
        for (Currency model : currencies) {
            result.add(convert(model, assetsByCurrency.get(model.getId())));
        }
        return result;
    }

    @Override
    public CurrencyVO getCurrency(Integer currencyId) {
        return convert(currencyDao.select(currencyId));
    }

    @Override
    public CurrencyVO getDefaultCurrency() {
        return convert(currencyDao.selectDefault());

    }

    @Override
    public boolean isSymbolExists(String symbol) {
        return currencyDao.isSymbolExists(symbol);
    }

    @Override
    @Transactional
    public void remove(Integer currencyId) {
        currencyDao.delete(currencyId);
    }

    @Override
    public CurrencyVO createCurrency() {
        CurrencyVO result = new CurrencyVO();
        if (currencyDao.count() > 0 && isDefaultExists()) {
            result.setDefaultRate(Boolean.FALSE);
        } else {
            result.setDefaultRate(Boolean.TRUE);
            result.setRate(BigDecimal.ONE);
        }
        return result;
    }

    @Override
    public boolean isDefaultExists() {
        return currencyDao.isDefaultExists();
    }

    private CurrencyVO convert(Currency model) {
        CurrencyVO result = new CurrencyVO();
        result.setCurrencyId(model.getId());
        result.setRate(model.getRate());
        result.setSymbol(model.getSymbol());
        result.setDefaultRate(model.getByDefault());
        result.setRemovable(isRemovable(model));
        return result;
    }

    private boolean isRemovable(Currency model) {
        boolean defaultRate = model.getByDefault();
        boolean noAccounts = noAccounts(model.getId());
        return noAccounts && (!defaultRate || currencyDao.count() == 1);
    }

    private boolean noAccounts(Integer currencyId) {
        return currencyId != null && !currencyDao.isUsed(currencyId);
    }

    private CurrencyVO convert(Currency model, BigDecimal total) {
        CurrencyVO result = convert(model);
        result.setTotal(total);
        return result;
    }

    @Autowired
    public void setCurrencyDao(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }

    @Autowired
    public void setTotalService(TotalService totalService) {
        this.totalService = totalService;
    }

    @Autowired
    public void setEntitiesFactory(EntitiesFactory entitiesFactory) {
        this.entitiesFactory = entitiesFactory;
    }
}
