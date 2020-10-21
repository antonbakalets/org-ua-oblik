package org.ua.oblik.domain.dao;

import org.ua.oblik.domain.model.CurrencyTotal;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

class CurrencyRepositoryFragmentImpl implements CurrencyRepositoryFragment {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CurrencyTotal> assetsByCurrency() {
        return entityManager.createNativeQuery(
                "SELECT c.curr_id, c.symbol, c.by_default, c.rate, coalesce(a.sumtotal, 0) as sumtotal "
                        + "   FROM currency c "
                        + "   LEFT JOIN "
                        + "       (SELECT currency, sum(total) AS sumtotal "
                        + "             FROM account WHERE account.kind='ASSETS' GROUP BY currency) a "
                        + "       ON c.curr_id = a.currency", CurrencyTotal.MAPPING_NAME)
                .getResultList();
    }
}
