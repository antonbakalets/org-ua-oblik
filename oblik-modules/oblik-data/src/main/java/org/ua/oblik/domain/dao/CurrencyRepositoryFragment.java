package org.ua.oblik.domain.dao;

import org.ua.oblik.domain.model.CurrencyTotal;

import java.util.List;

public interface CurrencyRepositoryFragment {

    /**
     * Get all currencies with assets total.
     */
    List<CurrencyTotal> assetsByCurrency();
}
