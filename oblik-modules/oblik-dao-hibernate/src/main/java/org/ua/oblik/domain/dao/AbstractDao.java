package org.ua.oblik.domain.dao;

import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ua.oblik.domain.beans.Identifiable;
import org.ua.oblik.domain.beans.PaginationBean;

/**
 *
 * @author Anton Bakalets
 */
abstract class AbstractDao<I, T extends Identifiable<I>> implements DaoFacade<I, T> {

    protected static Logger LOG = LoggerFactory.getLogger(AbstractDao.class);
    
    @PersistenceContext
    protected EntityManager entityManager;
    
    protected Class<T> entityClass;
        
    public AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }
    
    @Override
    public void insert(T entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public void update(T entity) {
        getEntityManager().merge(entity);
    }

    @Override
    public void delete(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @Override
    public T select(I id) {
        return getEntityManager().find(entityClass, id);
    }

    @Override
    public boolean exists(I id) {
        return select(id) != null;
    }

    @Override
    public List<T> selectAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public List<T> selectRange(int[] range) {
        return selectRange(range[0], range[1] - range[0]);
    }
    
    @Override
    public List<T> selectRange(int skipResults, int maxResults) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq)
                .setFirstResult(skipResults)
                .setMaxResults(maxResults)
                .getResultList();
    }

    @Override
    public List<T> selectRange(PaginationBean paginationBean) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery cq = cb.createQuery();
        final Root<T> from = cq.from(entityClass);
        cq.select(from);
        addSorting(cb, cq, from, paginationBean);
        return getEntityManager().createQuery(cq)
                .setFirstResult(paginationBean.getSkipResults())
                .setMaxResults(paginationBean.getMaxResults())
                .getResultList();
    }

    @Override
    public long count() {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery cq = cb.createQuery();
        final Root<T> rt = cq.from(entityClass);
        cq.select(cb.count(rt));
        Query q = getEntityManager().createQuery(cq);
        return (Long) q.getSingleResult();
    }

    protected boolean isAscendingSortingDirection(PaginationBean paginationBean) {
        return paginationBean.getDir() == null || PaginationBean.ASC.equalsIgnoreCase(paginationBean.getDir());
    }

    protected void addSorting(final CriteriaBuilder cb, final CriteriaQuery cq,
            final Root<T> rt, final PaginationBean paginationBean) {
        if (paginationBean.getSort() != null) {
            if (isAscendingSortingDirection(paginationBean)) {
                cq.orderBy(cb.asc(rt.get(paginationBean.getSort())));
            } else {
                cq.orderBy(cb.desc(rt.get(paginationBean.getSort())));
            }
        }
    }
}
