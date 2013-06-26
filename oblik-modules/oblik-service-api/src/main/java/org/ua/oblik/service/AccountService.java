package org.ua.oblik.service;

import java.util.List;
import org.ua.oblik.service.beans.AccountVO;

/**
 *
 * @author Anton Bakalets
 */
public interface AccountService {

    AccountVO getAccount(Integer accountId);

    void save(AccountVO account);

    List<AccountVO> getExpenseAccounts();

    List<AccountVO> getIncomeAccounts();

    List<AccountVO> getAssetsAccounts();
    
}
