package org.ua.oblik.domain.dao;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.ua.oblik.domain.model.Currency;

/**
 *
 * @author Anton Bakalets
 */
public class CurrencyDaoImpl extends AbstractDao<Integer, Currency> implements CurrencyDao {

    public CurrencyDaoImpl() {
        super(Currency.class);
    }

    @Override
    public Currency selectDefault() throws NoResultException {
        final CriteriaBuilder cbuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Currency> cquery = cbuilder.createQuery(Currency.class);
        final Root<Currency> root = cquery.from(Currency.class);
        cquery.select(root).where(cbuilder.equal(root.<Boolean>get("byDefault"), Boolean.TRUE));
        return entityManager.createQuery(cquery).getSingleResult();
    }
    
	@Override
	public boolean isSymbolExists(String symbol) {
		final CriteriaBuilder cbuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> cquery = cbuilder.createQuery(Long.class);
		final Root<Currency> root = cquery.from(Currency.class);
		cquery.select(cbuilder.count(root)).where(
				cbuilder.equal(root.<String>get("symbol"), symbol));
		return entityManager.createQuery(cquery).getSingleResult() > 0;
	}
}
