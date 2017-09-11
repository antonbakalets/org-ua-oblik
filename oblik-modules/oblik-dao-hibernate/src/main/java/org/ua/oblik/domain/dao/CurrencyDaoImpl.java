package org.ua.oblik.domain.dao;

import org.ua.oblik.domain.model.*;

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
public class CurrencyDaoImpl extends AbstractDao<Integer, Currency, CurrencyEntity> implements CurrencyDao {

    public CurrencyDaoImpl() {
        super(CurrencyEntity.class);
    }

    @Override
    public Currency selectDefault() {
        CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<CurrencyEntity> cquery = cbuilder.createQuery(CurrencyEntity.class);
        Root<CurrencyEntity> root = cquery.from(CurrencyEntity.class);
        cquery.select(root).where(cbuilder.equal(root.get(CurrencyEntity_.byDefault), Boolean.TRUE));
        return getEntityManager().createQuery(cquery).getSingleResult();
    }

    @Override
    public boolean isSymbolExists(String symbol) {
        CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> cquery = cbuilder.createQuery(Long.class);
        Root<CurrencyEntity> root = cquery.from(CurrencyEntity.class);
        cquery.select(cbuilder.count(root)).where(
                cbuilder.equal(root.get(CurrencyEntity_.symbol), symbol));
        return getEntityManager().createQuery(cquery).getSingleResult() > 0;
    }

    @Override
    public boolean isDefaultExists() {
        CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> cquery = cbuilder.createQuery(Long.class);
        Root<CurrencyEntity> root = cquery.from(CurrencyEntity.class);
        cquery.select(cbuilder.count(root)).where(
                cbuilder.equal(root.get(CurrencyEntity_.byDefault), Boolean.TRUE));
        return getEntityManager().createQuery(cquery).getSingleResult() > 0;
    }

    @Override
    public Map<Integer, BigDecimal> assetsByCurrencyId() {
        List<Object[]> list = getEntityManager().createNativeQuery("select currency.curr_id, accounts1_.sumtotal "
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
        CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> cquery = cbuilder.createQuery(Long.class);
        Root<AccountEntity> root = cquery.from(AccountEntity.class);
        cquery.select(cbuilder.count(root)).where(
                cbuilder.equal(root.get(AccountEntity_.currency), currencyId));
        return getEntityManager().createQuery(cquery).getSingleResult() > 0;
    }
}
