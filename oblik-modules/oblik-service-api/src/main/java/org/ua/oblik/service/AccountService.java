package org.ua.oblik.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.ua.oblik.service.beans.AccountCriteria;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.CurrencyVO;

/**
 *
 * @author Anton Bakalets
 */
public interface AccountService {

    AccountVO getAccount(Integer accountId);

    void save(AccountVO account);

    void delete(Integer accountId) throws NotFoundException, BusinessConstraintException;

    List<AccountVO> getAccounts(AccountCriteria criteria);

    BigDecimal totalAssets();

    Map<CurrencyVO, BigDecimal> totalAssetsByCurrency();

    boolean isNameExists(String name);
}
