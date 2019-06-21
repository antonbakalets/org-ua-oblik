package org.ua.oblik.domain.model;

/*
 * For objects identifiable by id.
 * @param <T> id type.
 * @author Anton Bakalets
 */
public interface Identifiable<T> {
    
    /** 
     * Return the identifier of this entity.
     * @return the identifier of this entity.
     */
    T getId();

    /**
     * Set the identifier of this entity.
     * @param id id to set.
     */
    void setId(T id);
}
