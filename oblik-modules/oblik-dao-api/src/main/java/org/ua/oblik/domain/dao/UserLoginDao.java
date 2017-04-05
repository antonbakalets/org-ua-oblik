package org.ua.oblik.domain.dao;

import java.util.Optional;

import org.ua.oblik.domain.model.UserLogin;

/**
 *
 * @author Anton Bakalets
 */
public interface UserLoginDao {

    Optional<UserLogin> loadUserLogin(String username);
    
}
