package org.ua.oblik.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
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

    List<Account> findByKind(AccountKind kind);

    boolean existsByShortName(String shortName);

    BigDecimal calculateTotal(Integer currencyId);

    BigDecimal calculateDefaultTotal();

    boolean isUsed(Integer accountId);
}
