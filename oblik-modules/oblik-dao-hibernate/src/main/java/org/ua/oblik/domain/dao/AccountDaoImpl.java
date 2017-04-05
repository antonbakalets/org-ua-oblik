package org.ua.oblik.domain.dao;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import org.ua.oblik.domain.beans.AccountKind;
import org.ua.oblik.domain.model.*;

/**
 *
 * @author Anton Bakalets
 */
public class AccountDaoImpl extends AbstractDao<Integer, Account, AccountEntity> implements AccountDao {

    public AccountDaoImpl() {
        super(AccountEntity.class);
    }

    @Override
    public List<? extends Account> selectByKind(AccountKind accountKind) {
        final CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<AccountEntity> cquery = cbuilder.createQuery(AccountEntity.class);
        final Root<AccountEntity> root = cquery.from(AccountEntity.class);
        cquery.select(root).where(cbuilder.equal(root.<AccountKind>get("kind"), accountKind));
        return getEntityManager().createQuery(cquery).getResultList();
    }

    @Override
    public BigDecimal calculateTotal(Currency currency) {
        final CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<BigDecimal> cquery = cbuilder.createQuery(BigDecimal.class);
        final Root<AccountEntity> root = cquery.from(AccountEntity.class);
        Path<BigDecimal> path = root.get("total");
        cquery.select(cbuilder.sum(path)).where(
                cbuilder.equal(root.<CurrencyEntity>get("currency"), currency),
                cbuilder.equal(root.<AccountKind>get("kind"), AccountKind.ASSETS));
        final BigDecimal sum = getEntityManager().createQuery(cquery).getSingleResult();
        return sum == null ? BigDecimal.ZERO : sum;
    }

    @Override
    public boolean isNameExists(String shortName) {
        final CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> cquery = cbuilder.createQuery(Long.class);
        final Root<AccountEntity> root = cquery.from(AccountEntity.class);
        cquery.select(cbuilder.count(root)).where(
                cbuilder.equal(root.<String>get("shortName"), shortName));
        return getEntityManager().createQuery(cquery).getSingleResult() > 0;
    }

    @Override
    public boolean isUsed(Integer accountId) {
        final CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> cquery = cbuilder.createQuery(Long.class);
        final Root<TxactionEntity> root = cquery.from(TxactionEntity.class);
        Long count = (long) 0;
        cquery.select(cbuilder.count(root)).where(
                cbuilder.equal(root.<Integer>get("debet"), accountId));
        count += getEntityManager().createQuery(cquery).getSingleResult();
        cquery.select(cbuilder.count(root)).where(
                cbuilder.equal(root.<Integer>get("credit"), accountId));
        count += getEntityManager().createQuery(cquery).getSingleResult();
        return count > 0;
    }

    @Override
    public BigDecimal calculateDefaultTotal() {
        final CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<BigDecimal> cquery = cbuilder.createQuery(BigDecimal.class);
        final Root<AccountEntity> root = cquery.from(AccountEntity.class);
        final Join<AccountEntity, CurrencyEntity> join = root.join("currency");
        final Expression<BigDecimal> product = cbuilder.prod(
                root.<BigDecimal>get("total"), join.<BigDecimal>get("rate"));
        cquery.select(cbuilder.sum(product)).where(
                cbuilder.equal(root.<AccountKind>get("kind"), AccountKind.ASSETS));
        final BigDecimal sum = getEntityManager().createQuery(cquery).getSingleResult();
        return sum == null ? BigDecimal.ZERO : sum;
    }
}
