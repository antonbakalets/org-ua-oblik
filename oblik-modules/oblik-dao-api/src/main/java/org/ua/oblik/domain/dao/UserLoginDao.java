package org.ua.oblik.domain.dao;

import org.ua.oblik.domain.model.UserLogin;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Anton Bakalets
 */
public interface UserLoginDao {

    Optional<UserLogin> loadUserLogin(String username);

    List<UserLogin> selectAll();
}
