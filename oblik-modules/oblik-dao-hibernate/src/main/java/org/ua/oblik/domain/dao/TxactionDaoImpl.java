package org.ua.oblik.domain.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ua.oblik.domain.model.Txaction;

/**
 *
 * @author Anton Bakalets
 */
public class TxactionDaoImpl extends AbstractDao<Integer, Txaction> implements TxactionDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TxactionDaoImpl.class);
    
    public TxactionDaoImpl() {
        super(Txaction.class);
    }

    @Override
    public List<Txaction> selectByMonth(Date date) {
        return selectByDateRange(DateUtils.getMonthBegining(date), DateUtils.getMonthEnd(date));
    }

    @Override
    public List<Txaction> selectByDateRange(Date start, Date end) {
        LOGGER.debug("Selecting Txactions in date range [" + start + ", " + end + "]");
        final CriteriaBuilder cbuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Txaction> cquery = cbuilder.createQuery(Txaction.class);
        final Root<Txaction> root = cquery.from(Txaction.class);
        cquery.select(root).where(
                cbuilder.greaterThanOrEqualTo(root.<Date>get("txDate"), start),
                cbuilder.lessThanOrEqualTo(root.<Date>get("txDate"), end));
        return entityManager.createQuery(cquery).getResultList();
    }

    @Override
    public List<Txaction> selectAll() {
        final CriteriaBuilder cbuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Txaction> cquery = cbuilder.createQuery(Txaction.class);
        final Root<Txaction> root = cquery.from(Txaction.class);
        cquery.select(root).orderBy(cbuilder.desc(root.get("txDate")));
        return getEntityManager().createQuery(cquery).getResultList();
    }
}
