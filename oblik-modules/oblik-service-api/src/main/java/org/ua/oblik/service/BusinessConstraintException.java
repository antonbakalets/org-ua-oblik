package org.ua.oblik.service;

/**
 * Validation error on service layer.
 */
public class BusinessConstraintException extends Exception {

    public BusinessConstraintException(String message) {
        super(message);
    }
}
