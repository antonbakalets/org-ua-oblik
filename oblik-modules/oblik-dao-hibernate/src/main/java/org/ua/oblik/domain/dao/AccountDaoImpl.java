package org.ua.oblik.domain.dao;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.ua.oblik.domain.model.AccountKind;
import org.ua.oblik.domain.model.AccountEntity;
import org.ua.oblik.domain.model.AccountEntity_;
import org.ua.oblik.domain.model.Currency;
import org.ua.oblik.domain.model.CurrencyEntity;
import org.ua.oblik.domain.model.CurrencyEntity_;
import org.ua.oblik.domain.model.TxactionEntity;
import org.ua.oblik.domain.model.TxactionEntity_;

/**
 * Account DAO.
 *
 * @author Anton Bakalets
 */
public class AccountDaoImpl implements AccountRepositoryFragment {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public BigDecimal calculateTotal(Currency currency) {
        CriteriaBuilder cbuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> cquery = cbuilder.createQuery(BigDecimal.class);
        Root<AccountEntity> root = cquery.from(AccountEntity.class);
        Path<BigDecimal> path = root.get(AccountEntity_.total);
        cquery.select(cbuilder.sum(path)).where(
                cbuilder.equal(root.get(AccountEntity_.currency), currency),
                cbuilder.equal(root.get(AccountEntity_.kind), AccountKind.ASSETS));
        BigDecimal sum = entityManager.createQuery(cquery).getSingleResult();
        return sum == null ? BigDecimal.ZERO : sum;
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
        Long count = 0L;
        cquery.select(cbuilder.count(root)).where(
                cbuilder.equal(root.get(TxactionEntity_.debet), accountId));
        count += entityManager.createQuery(cquery).getSingleResult();
        cquery.select(cbuilder.count(root)).where(
                cbuilder.equal(root.get(TxactionEntity_.credit), accountId));
        count += entityManager.createQuery(cquery).getSingleResult();
        return count > 0;
    }

    @Override
    public BigDecimal calculateDefaultTotal() {
        CriteriaBuilder cbuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> cquery = cbuilder.createQuery(BigDecimal.class);
        Root<AccountEntity> root = cquery.from(AccountEntity.class);
        Join<AccountEntity, CurrencyEntity> join = root.join(AccountEntity_.currency);
        Expression<BigDecimal> product = cbuilder.prod(
                root.get(AccountEntity_.total), join.get(CurrencyEntity_.rate));
        cquery.select(cbuilder.sum(product)).where(
                cbuilder.equal(root.get(AccountEntity_.kind), AccountKind.ASSETS));
        BigDecimal sum = entityManager.createQuery(cquery).getSingleResult();
        return sum == null ? BigDecimal.ZERO : sum;
    }
}
