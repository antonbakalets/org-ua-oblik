package org.ua.oblik.domain.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.ua.oblik.domain.model.Currency;
import org.ua.oblik.domain.model.CurrencyEntity;

/**
 *
 * @author Anton Bakalets
 */
public class CurrencyDaoImpl extends AbstractDao<Integer, Currency, CurrencyEntity> implements CurrencyDao {

    public CurrencyDaoImpl() {
        super(CurrencyEntity.class);
    }

    @Override
    public Currency selectDefault() {
        final CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<CurrencyEntity> cquery = cbuilder.createQuery(CurrencyEntity.class);
        final Root<CurrencyEntity> root = cquery.from(CurrencyEntity.class);
        cquery.select(root).where(cbuilder.equal(root.<Boolean>get("byDefault"), Boolean.TRUE));
        return getEntityManager().createQuery(cquery).getSingleResult();
    }

    @Override
    public boolean isSymbolExists(String symbol) {
        final CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> cquery = cbuilder.createQuery(Long.class);
        final Root<CurrencyEntity> root = cquery.from(CurrencyEntity.class);
        cquery.select(cbuilder.count(root)).where(
                cbuilder.equal(root.<String>get("symbol"), symbol));
        return getEntityManager().createQuery(cquery).getSingleResult() > 0;
    }

    @Override
    public boolean isDefaultExists() {
        final CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> cquery = cbuilder.createQuery(Long.class);
        final Root<CurrencyEntity> root = cquery.from(CurrencyEntity.class);
        cquery.select(cbuilder.count(root)).where(
                cbuilder.equal(root.<Boolean>get("byDefault"), Boolean.TRUE));
        return getEntityManager().createQuery(cquery).getSingleResult() > 0;
    }

    @Override
    public Map<Integer, BigDecimal> assetsByCurrencyId() {
        final List<Object[]> list = getEntityManager().createNativeQuery("select currency.curr_id, accounts1_.sumtotal "
                + "from currency currency left join "
                + "(select currency, sum(total) as sumtotal from account where account.kind='ASSETS' group by currency) "
                + "accounts1_ on currency.curr_id=accounts1_.currency "
                + "group by currency.curr_id").getResultList();
        final Map<Integer, BigDecimal> map = new HashMap<>();
        for (Object[] obs : list) {
            map.put((Integer) obs[0], (BigDecimal) obs[1]);
        }
        return map;
    }
}
