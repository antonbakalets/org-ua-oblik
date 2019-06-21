package org.ua.oblik.domain.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.ua.oblik.domain.model.Identifiable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Base DAO.
 *
 * @author Anton Bakalets
 */
abstract class AbstractDao<I, T extends Identifiable<I>, R extends T> implements DaoFacade<I, T> {

    @PersistenceContext
    private EntityManager entityManager;

    protected final Class<R> entityClass;

    public AbstractDao(Class<R> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
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
    public void delete(I id) {
        getEntityManager().remove(getOne(id));
    }

    @Override
    public T getOne(I id) {
        return getEntityManager().find(entityClass, id);
    }

    @Override
    public boolean existsById(I id) {
        return getOne(id) != null;
    }

    @Override
    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery cq = cb.createQuery();
        final Root<T> from = cq.from(entityClass);
        cq.select(from);
        addSorting(cb, cq, from, pageable);
        List resultList = getEntityManager().createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults((int) pageable.getOffset() + pageable.getPageSize())
                .getResultList();
        return new PageImpl<T>(resultList, pageable, count());
    }

    protected long count() {
        return 0;
    }

    protected boolean isAscendingSortingDirection(Sort.Order order) {
        return Sort.Direction.ASC == order.getDirection();
    }

    protected void addSorting(final CriteriaBuilder cb, final CriteriaQuery cq,
                              final Root<T> rt, final Pageable pageable) {
        Sort sort = pageable.getSort();
        if (sort != null) {
            for (Sort.Order order : sort) {
                if (isAscendingSortingDirection(order)) {
                    cq.orderBy(cb.asc(rt.get(order.getProperty())));
                } else {
                    cq.orderBy(cb.desc(rt.get(order.getProperty())));
                }
            }
        }
    }
}
