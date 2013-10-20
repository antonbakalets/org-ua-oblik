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
import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.Currency;
import org.ua.oblik.domain.model.Txaction;

/**
 *
 * @author Anton Bakalets
 */
public class AccountDaoImpl extends AbstractDao<Integer, Account> implements AccountDao {

    public AccountDaoImpl() {
        super(Account.class);
    }

    @Override
    public List<Account> selectByKind(AccountKind accountKind) {
        final CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Account> cquery = cbuilder.createQuery(Account.class);
        final Root<Account> root = cquery.from(Account.class);
        cquery.select(root).where(cbuilder.equal(root.<AccountKind>get("kind"), accountKind));
        return getEntityManager().createQuery(cquery).getResultList();
    }

    @Override
    public BigDecimal calculateTotal(Currency currency) {
        final CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<BigDecimal> cquery = cbuilder.createQuery(BigDecimal.class);
        final Root<Account> root = cquery.from(Account.class);
        Path<BigDecimal> path = root.<BigDecimal>get("total");
        cquery.select(cbuilder.sum(path)).where(
                cbuilder.equal(root.<Currency>get("currency"), currency),
                cbuilder.equal(root.<AccountKind>get("kind"), AccountKind.ASSETS));
        final BigDecimal sum = getEntityManager().createQuery(cquery).getSingleResult();
        return sum == null ? BigDecimal.ZERO : sum;
    }

    @Override
    public boolean isNameExists(String shortName) {
        final CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> cquery = cbuilder.createQuery(Long.class);
        final Root<Account> root = cquery.from(Account.class);
        cquery.select(cbuilder.count(root)).where(
                cbuilder.equal(root.<String>get("shortName"), shortName));
        return getEntityManager().createQuery(cquery).getSingleResult() > 0;
    }

    @Override
    public boolean isUsed(Integer accountId) {
        final CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> cquery = cbuilder.createQuery(Long.class);
        final Root<Txaction> root = cquery.from(Txaction.class);
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
        final Root<Account> root = cquery.from(Account.class);
        final Join<Account, Currency> join = root.join("currency");
        final Expression<BigDecimal> product = cbuilder.prod(
                root.<BigDecimal>get("total"), join.<BigDecimal>get("rate"));
        cquery.select(cbuilder.sum(product)).where(
                cbuilder.equal(root.<AccountKind>get("kind"), AccountKind.ASSETS));
        final BigDecimal sum = getEntityManager().createQuery(cquery).getSingleResult();
        return sum == null ? BigDecimal.ZERO : sum;
    }
}
