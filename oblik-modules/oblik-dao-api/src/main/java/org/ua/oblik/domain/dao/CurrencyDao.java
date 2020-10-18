package org.ua.oblik.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ua.oblik.domain.model.Currency;

/**
 *
 * @author Anton Bakalets
 */
@Repository
public interface CurrencyDao extends JpaRepository<Currency, Integer>, CurrencyRepositoryFragment {

    Currency findByByDefaultTrue();

    boolean existsByByDefaultTrue();

    boolean existsBySymbol(String symbol);

    long count();
}
