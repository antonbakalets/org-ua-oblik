package org.ua.oblik.domain.dao;

import org.ua.oblik.domain.model.CurrencyTotal;

import java.util.List;

public interface CurrencyRepositoryFragment {

    List<CurrencyTotal> assetsByCurrencyId();

    boolean isUsed(Integer currencyId);

}
