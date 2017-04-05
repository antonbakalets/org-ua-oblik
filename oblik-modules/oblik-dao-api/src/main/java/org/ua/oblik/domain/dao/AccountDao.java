package org.ua.oblik.domain.dao;

import org.ua.oblik.domain.beans.AccountKind;
import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.Currency;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao extends DaoFacade<Integer, Account> {

    List<? extends Account> selectByKind(AccountKind accountKind);

    BigDecimal calculateTotal(Currency currency);

    BigDecimal calculateDefaultTotal();
    
    boolean isNameExists(String name);

    boolean isUsed(Integer accountId);
}
