package org.ua.oblik.domain.dao;

import java.util.List;
import org.ua.oblik.domain.beans.Identifiable;
import org.ua.oblik.domain.beans.PaginationBean;

/**
 * Common data access object methods.
 * @param <I> identifier type.
 * @param <T> identifiable object type.
 * @author Anton Bakalets
 */
public interface DaoFacade<I, T extends Identifiable<I>> {

    long count();

    void delete(I id);

    void insert(T entity);

    void update(T entity);
    
    boolean exists(I id);
    
    T select(I id);

    List<T> selectAll();

    List<T> selectRange(int[] range);

    List<T> selectRange(int skipResults, int maxResults);
    
    List<T> selectRange(PaginationBean paginationBean);
}
