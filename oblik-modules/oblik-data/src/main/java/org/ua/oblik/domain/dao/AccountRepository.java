package org.ua.oblik.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.AccountKind;

import java.math.BigDecimal;
import java.util.List;

/**
 * Account repository.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    /**
     * Search accounts by kind.
     */
    List<Account> findByKind(AccountKind kind);

    /**
     * Validate account with specific name exists.
     */
    boolean existsByShortName(String shortName);

    /**
     * Calculate specific currency assets total.
     */
    @Query("SELECT COALESCE( SUM(a.total), 0) "
            + "   FROM Account a "
            + "   WHERE a.currency.currId = :currencyId AND a.kind = 'ASSETS'")
    BigDecimal calculateTotal(Integer currencyId);

    /**
     * Calculate all currency assets total in default currency.
     */
    @Query("SELECT COALESCE( SUM(a.total * c.rate), 0) "
            + "   FROM Account a "
            + "   JOIN a.currency c "
            + "   WHERE a.kind = 'ASSETS'")
    BigDecimal calculateDefaultTotal();

    /**
     * Check whether account is in use by counting transactions associated with this account.
     */
    default boolean isInUse(Integer accountId) {
        return countInUse(accountId) > 0;
    }

    /**
     * Count transactions associated with this account.
     */
    @Query("SELECT COUNT(t) FROM Txaction t WHERE t.credit.accoId = :accountId OR t.debet.accoId = :accountId")
    long countInUse(Integer accountId);
}
