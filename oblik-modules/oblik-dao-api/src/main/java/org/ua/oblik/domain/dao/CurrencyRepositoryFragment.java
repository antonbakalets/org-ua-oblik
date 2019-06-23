package org.ua.oblik.domain.dao;

import org.ua.oblik.domain.model.Currency;

import java.math.BigDecimal;
import java.util.Map;

public interface CurrencyRepositoryFragment {

    Currency selectDefault();

    boolean isSymbolExists(String symbol);

    boolean isDefaultExists();

    Map<Integer, BigDecimal> assetsByCurrencyId();

    boolean isUsed(Integer currencyId);

    long count();
}
