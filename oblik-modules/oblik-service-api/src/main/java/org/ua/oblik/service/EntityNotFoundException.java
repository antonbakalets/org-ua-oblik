package org.ua.oblik.service;

/**
 *
 * @author Anton Bakalets
 */
public class EntityNotFoundException extends RuntimeException {


    public EntityNotFoundException(String message) {
        super(message);
    }
}
