package org.ua.oblik.domain.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.ua.oblik.domain.model.Identifiable;

import java.util.List;

/**
 * Common data access object methods.
 * @param <I> identifier type.
 * @param <T> identifiable object type.
 * @author Anton Bakalets
 */
public interface DaoFacade<I, T extends Identifiable<I>> {

    void delete(I id);

    void insert(T entity);

    void update(T entity);
    
    boolean existsById(I id);
    
    T getOne(I id);

    List<T> findAll();

    Page<T> findAll(Pageable pageable);
}
