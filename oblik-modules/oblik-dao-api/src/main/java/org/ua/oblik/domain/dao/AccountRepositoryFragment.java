package org.ua.oblik.domain.dao;

import java.math.BigDecimal;

public interface AccountRepositoryFragment {

    BigDecimal calculateTotal(Integer currencyId);

    BigDecimal calculateDefaultTotal();

    boolean isNameExists(String name);

    boolean isUsed(Integer accountId);
}
