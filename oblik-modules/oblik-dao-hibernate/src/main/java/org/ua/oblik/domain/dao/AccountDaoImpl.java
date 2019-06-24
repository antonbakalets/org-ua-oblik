package org.ua.oblik.domain.dao;

import org.ua.oblik.domain.model.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.math.BigDecimal;

/**
 * Account DAO.
 *
 * @author Anton Bakalets
 */
public class AccountDaoImpl implements AccountRepositoryFragment {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BigDecimal calculateTotal(Integer currencyId) {
        CriteriaBuilder cbuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> cquery = cbuilder.createQuery(BigDecimal.class);
        Root<AccountEntity> root = cquery.from(AccountEntity.class);
        cquery.select(cbuilder.coalesce(cbuilder.sum(root.get(AccountEntity_.total)), BigDecimal.ZERO))
                .where(
                        cbuilder.equal(root.get(AccountEntity_.currency), currencyId),
                        cbuilder.equal(root.get(AccountEntity_.kind), AccountKind.ASSETS)
                );
        return entityManager.createQuery(cquery).getSingleResult();
    }

    @Override
    public boolean isNameExists(String shortName) {
        CriteriaBuilder cbuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cquery = cbuilder.createQuery(Long.class);
        Root<AccountEntity> root = cquery.from(AccountEntity.class);
        cquery.select(cbuilder.count(root)).where(
                cbuilder.equal(root.get(AccountEntity_.shortName), shortName));
        return entityManager.createQuery(cquery).getSingleResult() > 0;
    }

    @Override
    public boolean isUsed(Integer accountId) {
        CriteriaBuilder cbuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cquery = cbuilder.createQuery(Long.class);
        Root<TxactionEntity> root = cquery.from(TxactionEntity.class);
        cquery.select(cbuilder.count(root)).where(
                cbuilder.or(
                        cbuilder.equal(root.get(TxactionEntity_.debet), accountId),
                        cbuilder.equal(root.get(TxactionEntity_.credit), accountId)));
        return entityManager.createQuery(cquery).getSingleResult() > 0;
    }

    @Override
    public BigDecimal calculateDefaultTotal() {
        CriteriaBuilder cbuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> cquery = cbuilder.createQuery(BigDecimal.class);
        Root<AccountEntity> root = cquery.from(AccountEntity.class);

        Join<AccountEntity, CurrencyEntity> join = root.join(AccountEntity_.currency);
        Expression<BigDecimal> product = cbuilder.prod(
                root.get(AccountEntity_.total),
                join.get(CurrencyEntity_.rate));

        cquery.select(cbuilder.coalesce(cbuilder.sum(product), BigDecimal.ZERO))
                .where(cbuilder.equal(root.get(AccountEntity_.kind), AccountKind.ASSETS));

        return entityManager.createQuery(cquery).getSingleResult();
    }
}
