package org.ua.oblik.domain.dao;

/**
 *
 * @author Anton Bakalets
 */
public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("Cannot find user.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
