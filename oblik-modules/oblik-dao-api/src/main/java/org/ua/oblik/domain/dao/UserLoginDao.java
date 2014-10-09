package org.ua.oblik.domain.dao;

import org.ua.oblik.domain.model.UserLoginEntity;

/**
 *
 * @author Anton Bakalets
 */
public interface UserLoginDao {

    UserLoginEntity loadUserLogin(String username) throws UserNotFoundException;
    
}
