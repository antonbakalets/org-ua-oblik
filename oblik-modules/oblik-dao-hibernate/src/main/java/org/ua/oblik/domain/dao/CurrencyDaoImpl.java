package org.ua.oblik.domain.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.ua.oblik.domain.model.AccountEntity;
import org.ua.oblik.domain.model.AccountEntity_;
import org.ua.oblik.domain.model.Currency;
import org.ua.oblik.domain.model.CurrencyEntity;
import org.ua.oblik.domain.model.CurrencyEntity_;

/**
 * Currency DAO.
 *
 * @author Anton Bakalets
 */
public class CurrencyDaoImpl implements CurrencyRepositoryFragment {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Currency selectDefault() {
        CriteriaBuilder cbuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CurrencyEntity> cquery = cbuilder.createQuery(CurrencyEntity.class);
        Root<CurrencyEntity> root = cquery.from(CurrencyEntity.class);
        cquery.select(root).where(cbuilder.equal(root.get(CurrencyEntity_.byDefault), Boolean.TRUE));
        return entityManager.createQuery(cquery).getSingleResult();
    }

    @Override
    public boolean isSymbolExists(String symbol) {
        CriteriaBuilder cbuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cquery = cbuilder.createQuery(Long.class);
        Root<CurrencyEntity> root = cquery.from(CurrencyEntity.class);
        cquery.select(cbuilder.count(root)).where(
                cbuilder.equal(root.get(CurrencyEntity_.symbol), symbol));
        return entityManager.createQuery(cquery).getSingleResult() > 0;
    }

    @Override
    public boolean isDefaultExists() {
        CriteriaBuilder cbuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cquery = cbuilder.createQuery(Long.class);
        Root<CurrencyEntity> root = cquery.from(CurrencyEntity.class);
        cquery.select(cbuilder.count(root)).where(
                cbuilder.equal(root.get(CurrencyEntity_.byDefault), Boolean.TRUE));
        return entityManager.createQuery(cquery).getSingleResult() > 0;
    }

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

    @Override
    public long count() {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery cq = cb.createQuery();
        final Root<Currency> rt = cq.from(CurrencyEntity.class);
        cq.select(cb.count(rt));
        Query q = entityManager.createQuery(cq);
        return (Long) q.getSingleResult();
    }
}
