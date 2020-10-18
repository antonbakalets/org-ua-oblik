package org.ua.oblik.domain.dao;

import java.util.Optional;

import org.springframework.data.repository.Repository;
import org.ua.oblik.domain.model.UserLogin;

/**
 *
 * @author Anton Bakalets
 */
@org.springframework.stereotype.Repository
public interface UserLoginDao extends Repository<UserLogin, Integer> {

    Optional<UserLogin> findByUsernameIgnoreCase(String username);
    
}
