package org.ua.oblik.domain.dao;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import org.ua.oblik.domain.beans.AccountKind;
import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.Currency;

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
        final CriteriaBuilder cbuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Account> cquery = cbuilder.createQuery(Account.class);
        final Root<Account> root = cquery.from(Account.class);
        cquery.select(root).where(cbuilder.equal(root.<AccountKind>get("kind"), accountKind));
        return entityManager.createQuery(cquery).getResultList();
    }

    @Override
    public BigDecimal calculateTotal(Currency currency) {
        final CriteriaBuilder cbuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<BigDecimal> cquery = cbuilder.createQuery(BigDecimal.class);
        final Root<Account> root = cquery.from(Account.class);
        Path<BigDecimal> path = root.<BigDecimal>get("total");
        cquery.select(cbuilder.sum(path)).where(
                cbuilder.equal(root.<Currency>get("currency"), currency),
                cbuilder.equal(root.<AccountKind>get("kind"), AccountKind.ASSETS));
        return entityManager.createQuery(cquery).getSingleResult();
    }
}
