package org.ua.oblik.domain.dao;

import java.math.BigDecimal;
import java.util.List;
import org.ua.oblik.domain.beans.AccountKind;
import org.ua.oblik.domain.model.AccountEntity;
import org.ua.oblik.domain.model.CurrencyEntity;

/**
 *
 * @author Anton Bakalets
 */
public interface AccountDao extends DaoFacade<Integer, AccountEntity> {

    List<? extends AccountEntity> selectByKind(AccountKind accountKind);

    BigDecimal calculateTotal(CurrencyEntity currency);

    BigDecimal calculateDefaultTotal();
    
    boolean isNameExists(String name);

    boolean isUsed(Integer accountId);
}
