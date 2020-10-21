package org.ua.oblik.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ua.oblik.domain.model.Currency;
import org.ua.oblik.domain.model.CurrencyTotal;

import java.util.List;

/**
 * Currency repository.
 */
@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    Currency findByByDefaultTrue();

    boolean existsByByDefaultTrue();

    boolean existsBySymbol(String symbol);

    List<CurrencyTotal> assetsByCurrency();

    boolean isUsed(Integer currencyId);
}
