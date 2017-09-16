package org.ua.oblik.service;

import java.util.List;

import org.ua.oblik.service.beans.CurrencyVO;

public interface CurrencyService {

    CurrencyVO createCurrency();

    CurrencyVO getCurrency(Integer currencyId);

    CurrencyVO getDefaultCurrency();

    CurrencyVO save(CurrencyVO cvo) throws NotFoundException, BusinessConstraintException;

    List<CurrencyVO> getCurrencies();

    boolean isDefaultExists();

    boolean isSymbolExists(String symbol);

    /**
     * Remove currency.
     *
     * @param currencyId the id
     * @throws NotFoundException if could not find currency by id
     * @throws BusinessConstraintException if currency is default or used
     */
    void remove(Integer currencyId) throws NotFoundException, BusinessConstraintException;
}
