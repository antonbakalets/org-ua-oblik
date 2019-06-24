package org.ua.oblik.service;

import org.ua.oblik.service.beans.AccountCriteria;
import org.ua.oblik.service.beans.AccountVO;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Anton Bakalets
 */
public interface AccountService {

    AccountVO getAccount(Integer accountId);

    void save(AccountVO account) throws NotFoundException, BusinessConstraintException;

    void delete(Integer accountId) throws NotFoundException, BusinessConstraintException;

    List<AccountVO> getAccounts(AccountCriteria criteria);

    BigDecimal totalAssets();

    boolean isNameExists(String name);
}
