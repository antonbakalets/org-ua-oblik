package org.ua.oblik.domain.beans;

/**
 *
 * @author Anton Bakalets
 */
public interface Identifiable<T> {
    
    /** 
     * Return the identifier of this entity.
     * @return the identifier of this entity.
     */
    public T getId();

    /**
     * Set the identifier of this entity.
     */
    public void setId(T id);
}
