package org.ua.oblik.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ua.oblik.domain.model.Currency;

/**
 * Currency repository.
 */
@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer>, CurrencyRepositoryFragment {

    /**
     * Find default currency.
     */
    Currency findByByDefaultTrue();

    /**
     * Check whether default currency exists.
     */
    boolean existsByByDefaultTrue();

    /**
     * Check whether currency symbol is in use.
     */
    boolean existsBySymbol(String symbol);

    /**
     * Check whether currency is in use by counting accounts.
     */
    default boolean isInUse(Integer currencyId) {
        return countInUse(currencyId) > 0;
    }

    /**
     * Count specific currency accounts.
     */
    @Query("SELECT COUNT(a) FROM Account a WHERE a.currency.currId = :currencyId")
    long countInUse(Integer currencyId);
}
