package org.ua.oblik.domain.dao;

import java.math.BigDecimal;
import java.util.List;

import org.ua.oblik.domain.model.AccountKind;
import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.Currency;

public interface AccountDao extends DaoFacade<Integer, Account> {

    List<Account> selectByKind(AccountKind accountKind);

    BigDecimal calculateTotal(Currency currency);

    BigDecimal calculateDefaultTotal();
    
    boolean isNameExists(String name);

    boolean isUsed(Integer accountId);
}
