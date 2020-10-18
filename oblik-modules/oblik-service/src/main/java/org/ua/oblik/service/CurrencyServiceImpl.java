package org.ua.oblik.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ua.oblik.domain.dao.CurrencyDao;
import org.ua.oblik.domain.model.Currency;
import org.ua.oblik.domain.model.CurrencyTotal;
import org.ua.oblik.domain.model.EntitiesFactory;
import org.ua.oblik.service.beans.CurrencyVO;

/**
 * @author Anton Bakalets
 */
@Service
public class CurrencyServiceImpl implements CurrencyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    private CurrencyDao currencyDao;
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
            LOGGER.debug("Saving new currency, symbol: {}.", cvo.getSymbol());
            currency.setByDefault(false);
            currency.setRate(cvo.getRate());
        } else {
            LOGGER.debug("Saving default currency, symbol: {}.", cvo.getSymbol());
            currency.setByDefault(true);
            currency.setRate(BigDecimal.ONE);
        }
        currencyDao.save(currency);
        cvo.setCurrencyId(currency.getId());
        cvo.setDefaultRate(currency.isByDefault());
    }

    private void update(CurrencyVO cvo) {
        LOGGER.debug("Updating currency, symbol: {}.", cvo.getSymbol());
        Currency currency = currencyDao.findById(cvo.getCurrencyId()).get();
        currency.setRate(cvo.getRate());
        currency.setSymbol(cvo.getSymbol());
    }

    @Override
    public List<CurrencyVO> getCurrencies() {
        return currencyDao.assetsByCurrencyId().stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private CurrencyVO convert(CurrencyTotal currencyTotal) {
        CurrencyVO result = new CurrencyVO();
        result.setCurrencyId(currencyTotal.getCurrId());
        result.setRate(currencyTotal.getRate());
        result.setSymbol(currencyTotal.getSymbol());
        result.setDefaultRate(currencyTotal.isByDefault());
        result.setRemovable(isRemovable(currencyTotal.isByDefault(), currencyTotal.getCurrId()));
        result.setTotal(currencyTotal.getTotal());
        return result;
    }

    @Override
    public CurrencyVO getCurrency(Integer currencyId) {
        return convert(currencyDao.findById(currencyId).get());
    }

    @Override
    public CurrencyVO getDefaultCurrency() {
        return convert(currencyDao.findByByDefaultTrue());

    }

    @Override
    public boolean isSymbolExists(String symbol) {
        return currencyDao.existsBySymbol(symbol);
    }

    @Override
    @Transactional(rollbackFor = {NotFoundException.class, BusinessConstraintException.class})
    public void remove(Integer currencyId) throws NotFoundException, BusinessConstraintException {
        Optional<Currency> currency = currencyDao.findById(currencyId);
        if (currency.isPresent()) {
            if (isRemovable(currency.get())) {
                currencyDao.delete(currency.get());
            } else {
                throw new BusinessConstraintException("Cannot remove");
            }
        } else {
            throw new NotFoundException("Could not find.");
        }
    }

    @Override
    public CurrencyVO createCurrency() {
        CurrencyVO result = new CurrencyVO();
        if (isDefaultExists()) {
            result.setDefaultRate(Boolean.FALSE);
        } else {
            result.setDefaultRate(Boolean.TRUE);
            result.setRate(BigDecimal.ONE);
        }
        return result;
    }

    @Override
    public boolean isDefaultExists() {
        return currencyDao.existsByByDefaultTrue();
    }

    private CurrencyVO convert(Currency model) {
        CurrencyVO result = new CurrencyVO();
        result.setCurrencyId(model.getId());
        result.setRate(model.getRate());
        result.setSymbol(model.getSymbol());
        result.setDefaultRate(model.isByDefault());
        result.setRemovable(isRemovable(model));
        return result;
    }

    private boolean isRemovable(Currency model) {
        boolean defaultRate = model.isByDefault();
        Integer currencyId = model.getId();
        return isRemovable(defaultRate, currencyId);
    }

    private boolean isRemovable(boolean defaultRate, Integer currencyId) {
        boolean noAccounts = currencyId != null && !currencyDao.isUsed(currencyId);
        return noAccounts && (!defaultRate || currencyDao.count() == 1);
    }

    @Autowired
    public void setCurrencyDao(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }

    @Autowired
    public void setEntitiesFactory(EntitiesFactory entitiesFactory) {
        this.entitiesFactory = entitiesFactory;
    }
}
