package org.ua.oblik.domain.dao;

import org.ua.oblik.domain.beans.AccountKind;
import org.ua.oblik.domain.model.*;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Account DAO.
 *
 * @author Anton Bakalets
 */
public class AccountDaoImpl extends AbstractDao<Integer, Account, AccountEntity> implements AccountDao {

    public AccountDaoImpl() {
        super(AccountEntity.class);
    }

    @Override
    public List<? extends Account> selectByKind(AccountKind accountKind) {
        CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<AccountEntity> cquery = cbuilder.createQuery(AccountEntity.class);
        Root<AccountEntity> root = cquery.from(AccountEntity.class);
        cquery.select(root).where(cbuilder.equal(root.get(AccountEntity_.kind), accountKind));
        return getEntityManager().createQuery(cquery).getResultList();
    }

    @Override
    public BigDecimal calculateTotal(Currency currency) {
        CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<BigDecimal> cquery = cbuilder.createQuery(BigDecimal.class);
        Root<AccountEntity> root = cquery.from(AccountEntity.class);
        Path<BigDecimal> path = root.get(AccountEntity_.total);
        cquery.select(cbuilder.sum(path)).where(
                cbuilder.equal(root.get(AccountEntity_.currency), currency),
                cbuilder.equal(root.get(AccountEntity_.kind), AccountKind.ASSETS));
        BigDecimal sum = getEntityManager().createQuery(cquery).getSingleResult();
        return sum == null ? BigDecimal.ZERO : sum;
    }

    @Override
    public boolean isNameExists(String shortName) {
        CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> cquery = cbuilder.createQuery(Long.class);
        Root<AccountEntity> root = cquery.from(AccountEntity.class);
        cquery.select(cbuilder.count(root)).where(
                cbuilder.equal(root.get(AccountEntity_.shortName), shortName));
        return getEntityManager().createQuery(cquery).getSingleResult() > 0;
    }

    @Override
    public boolean isUsed(Integer accountId) {
        CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> cquery = cbuilder.createQuery(Long.class);
        Root<TxactionEntity> root = cquery.from(TxactionEntity.class);
        Long count = 0L;
        cquery.select(cbuilder.count(root)).where(
                cbuilder.equal(root.get(TxactionEntity_.debet), accountId));
        count += getEntityManager().createQuery(cquery).getSingleResult();
        cquery.select(cbuilder.count(root)).where(
                cbuilder.equal(root.get(TxactionEntity_.credit), accountId));
        count += getEntityManager().createQuery(cquery).getSingleResult();
        return count > 0;
    }

    @Override
    public BigDecimal calculateDefaultTotal() {
        CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<BigDecimal> cquery = cbuilder.createQuery(BigDecimal.class);
        Root<AccountEntity> root = cquery.from(AccountEntity.class);
        Join<AccountEntity, CurrencyEntity> join = root.join(AccountEntity_.currency);
        Expression<BigDecimal> product = cbuilder.prod(
                root.get(AccountEntity_.total), join.get(CurrencyEntity_.rate));
        cquery.select(cbuilder.sum(product)).where(
                cbuilder.equal(root.get(AccountEntity_.kind), AccountKind.ASSETS));
        BigDecimal sum = getEntityManager().createQuery(cquery).getSingleResult();
        return sum == null ? BigDecimal.ZERO : sum;
    }
}
