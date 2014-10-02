package org.ua.oblik.service;

import java.util.ArrayList;
import java.util.List;
import org.ua.oblik.service.beans.AccountCriteria;
import org.ua.oblik.service.beans.AccountVO;

/**
 *
 * @author Anton Bakalets
 */
class AccountFilter {
    
    private AccountCriteria criteria = AccountCriteria.EMPTY_CRITERIA;

    public void setCriteria(AccountCriteria criteria) {
        this.criteria = criteria;
    }

    List<AccountVO> filter(List<AccountVO> list) {
        List<AccountVO> result = new ArrayList<>();
        for (AccountVO elem : list) {
            if (filterByType(elem) && filterBySymbol(elem)) {
                result.add(elem);
            }
        }
        return result;
    }

    private boolean filterByType(AccountVO elem) {
        return criteria.getType() == null || criteria.getType().equals(elem.getType());
    }

    private boolean filterBySymbol(AccountVO elem) {
        return criteria.getCurrencySymbol() == null ||
                criteria.getCurrencySymbol().equals(elem.getCurrencySymbol());
    }
}
