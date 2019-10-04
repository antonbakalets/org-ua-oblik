package org.ua.oblik.domain.dao;

import org.ua.oblik.domain.model.AccountEntity;
import org.ua.oblik.domain.model.AccountEntity_;
import org.ua.oblik.domain.model.CurrencyTotal;
import org.ua.oblik.domain.model.CurrencyTotalMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Currency DAO.
 *
 * @author Anton Bakalets
 */
public class CurrencyDaoImpl implements CurrencyRepositoryFragment {

    private static final String SQL_STRING =
            "SELECT c.curr_id, c.symbol, c.by_default, c.rate, coalesce(a.sumtotal, 0) as sumtotal " +
            "  FROM currency c " +
            "  LEFT JOIN (SELECT currency, sum(total) AS sumtotal FROM account WHERE account.kind='ASSETS' GROUP BY currency) a " +
            "    ON c.curr_id = a.currency";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CurrencyTotal> assetsByCurrencyId() {
        return entityManager.createNativeQuery(SQL_STRING, CurrencyTotalMapping.NAME).getResultList();
    }

    @Override
    public boolean isUsed(Integer currencyId) {
        CriteriaBuilder cbuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cquery = cbuilder.createQuery(Long.class);

        Root<AccountEntity> root = cquery.from(AccountEntity.class);
        cquery.select(cbuilder.count(root)).where(
                cbuilder.equal(root.get(AccountEntity_.currency), currencyId));
        return entityManager.createQuery(cquery).getSingleResult() > 0;
    }
}
