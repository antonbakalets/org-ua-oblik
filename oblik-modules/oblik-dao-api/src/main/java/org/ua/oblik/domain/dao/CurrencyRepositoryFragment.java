package org.ua.oblik.domain.dao;

import java.math.BigDecimal;
import java.util.Map;

public interface CurrencyRepositoryFragment {

    Map<Integer, BigDecimal> assetsByCurrencyId();

    boolean isUsed(Integer currencyId);

}
