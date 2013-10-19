package org.ua.oblik.domain.dao;

/**
 *
 * @author Anton Bakalets
 */
public class UserNotFoundException extends Exception {
    
    private static final String DEFAULT_MESSAGE = "Cannot find user.";

    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
    
    public UserNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
