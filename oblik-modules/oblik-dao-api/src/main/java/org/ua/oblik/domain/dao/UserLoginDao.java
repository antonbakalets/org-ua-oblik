package org.ua.oblik.domain.dao;

import org.ua.oblik.domain.model.UserLogin;

/**
 *
 * @author Anton Bakalets
 */
public interface UserLoginDao {

    UserLogin loadUserLogin(String username);
    
}
