package org.ua.oblik.domain.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ua.oblik.domain.model.Txaction;
import org.ua.oblik.domain.model.TxactionEntity;
import org.ua.oblik.domain.model.TxactionEntity_;

/**
 * Transactions DAO.
 *
 * @author Anton Bakalets
 */
public class TxactionDaoImpl extends AbstractDao<Integer, Txaction, TxactionEntity> implements TxactionDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TxactionDaoImpl.class);
    
    public TxactionDaoImpl() {
        super(TxactionEntity.class);
    }

    @Override
    public List<? extends Txaction> selectByMonth(Date date) {
        return selectByDateRange(DateUtils.getMonthBegining(date), DateUtils.getMonthEnd(date));
    }

    @Override
    public List<? extends Txaction> selectByDateRange(Date start, Date end) {
        LOGGER.debug("Selecting Txactions in date range [{}, {}].", start, end);
        final CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<TxactionEntity> cquery = cbuilder.createQuery(TxactionEntity.class);
        final Root<TxactionEntity> root = cquery.from(TxactionEntity.class);
        cquery.select(root).where(
                cbuilder.greaterThanOrEqualTo(root.get(TxactionEntity_.txDate), start),
                cbuilder.lessThanOrEqualTo(root.get(TxactionEntity_.txDate), end));
        return getEntityManager().createQuery(cquery).getResultList();
    }

    @Override
    public List<? extends Txaction> selectAll() {
        final CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<TxactionEntity> cquery = cbuilder.createQuery(TxactionEntity.class);
        final Root<TxactionEntity> root = cquery.from(TxactionEntity.class);
        cquery.select(root).orderBy(cbuilder.desc(root.get(TxactionEntity_.txDate)));
        return getEntityManager().createQuery(cquery).getResultList();
    }
}
