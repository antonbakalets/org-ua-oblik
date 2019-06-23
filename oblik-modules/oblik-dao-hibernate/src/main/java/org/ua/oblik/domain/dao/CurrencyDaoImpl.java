package org.ua.oblik.domain.dao;

import org.ua.oblik.domain.model.AccountEntity;
import org.ua.oblik.domain.model.AccountEntity_;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Currency DAO.
 *
 * @author Anton Bakalets
 */
public class CurrencyDaoImpl implements CurrencyRepositoryFragment {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map<Integer, BigDecimal> assetsByCurrencyId() {
        List<Object[]> list = entityManager.createNativeQuery("select currency.curr_id, accounts1_.sumtotal "
                + "from currency currency left join "
                + "(select currency, sum(total) as sumtotal from account where account.kind='ASSETS' group by currency) "
                + "accounts1_ on currency.curr_id=accounts1_.currency "
                + "group by currency.curr_id").getResultList();
        Map<Integer, BigDecimal> map = new HashMap<>();
        for (Object[] obs : list) {
            map.put((Integer) obs[0], (BigDecimal) obs[1]);
        }
        return map;
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
