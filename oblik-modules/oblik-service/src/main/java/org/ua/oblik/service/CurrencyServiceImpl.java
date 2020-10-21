package org.ua.oblik.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ua.oblik.domain.dao.CurrencyRepository;
import org.ua.oblik.domain.model.Currency;
import org.ua.oblik.domain.model.CurrencyTotal;
import org.ua.oblik.service.beans.CurrencyVO;
import org.ua.oblik.service.mapping.CurrencyMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Currency Service implementation.
 */
@Service
public class CurrencyServiceImpl implements CurrencyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyMapper currencyMapper;

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
        Currency currency = new Currency();
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
        currencyRepository.save(currency);
        cvo.setCurrencyId(currency.getCurrId());
        cvo.setDefaultRate(currency.isByDefault());
    }

    private void update(CurrencyVO cvo) {
        LOGGER.debug("Updating currency, symbol: {}.", cvo.getSymbol());
        Currency currency = currencyRepository.findById(cvo.getCurrencyId()).get();
        currency.setRate(cvo.getRate());
        currency.setSymbol(cvo.getSymbol());
    }

    @Override
    public List<CurrencyVO> getCurrencies() {
        return currencyRepository.assetsByCurrency().stream()
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
        Currency model = currencyRepository.findById(currencyId).get();
        CurrencyVO result = currencyMapper.toVO(model);
        result.setRemovable(isRemovable(model));
        return result;
    }

    @Override
    public CurrencyVO getDefaultCurrency() {
        Currency model = currencyRepository.findByByDefaultTrue();
        CurrencyVO result = currencyMapper.toVO(model);
        result.setRemovable(isRemovable(model));
        return result;
    }

    @Override
    public boolean isSymbolExists(String symbol) {
        return currencyRepository.existsBySymbol(symbol);
    }

    @Override
    @Transactional(rollbackFor = {NotFoundException.class, BusinessConstraintException.class})
    public void remove(Integer currencyId) throws NotFoundException, BusinessConstraintException {
        Optional<Currency> currency = currencyRepository.findById(currencyId);
        if (currency.isPresent()) {
            if (isRemovable(currency.get())) {
                currencyRepository.delete(currency.get());
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
        return currencyRepository.existsByByDefaultTrue();
    }

    private boolean isRemovable(Currency model) {
        boolean defaultRate = model.isByDefault();
        Integer currencyId = model.getCurrId();
        return isRemovable(defaultRate, currencyId);
    }

    private boolean isRemovable(boolean defaultRate, Integer currencyId) {
        boolean noAccounts = currencyId != null && !currencyRepository.isInUse(currencyId);
        return noAccounts && (!defaultRate || currencyRepository.count() == 1);
    }
}
