package org.ua.oblik.domain.dao;

import org.ua.oblik.domain.model.Currency;

import java.math.BigDecimal;

public interface AccountRepositoryFragment {

    BigDecimal calculateTotal(Currency currency);

    BigDecimal calculateDefaultTotal();

    boolean isNameExists(String name);

    boolean isUsed(Integer accountId);
}
