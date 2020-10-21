package org.ua.oblik.domain.dao;

import org.springframework.data.repository.Repository;
import org.ua.oblik.domain.model.UserLogin;

import java.util.Optional;

/**
 * User login repository.
 */
@org.springframework.stereotype.Repository
public interface UserLoginRepository extends Repository<UserLogin, Integer> {

    Optional<UserLogin> findByUsernameIgnoreCase(String username);
}
