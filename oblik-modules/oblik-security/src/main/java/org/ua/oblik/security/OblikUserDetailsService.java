package org.ua.oblik.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.ua.oblik.domain.dao.UserLoginDao;
import org.ua.oblik.domain.model.UserLogin;

import java.util.List;

public class OblikUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OblikUserDetailsService.class);

    private UserLoginDao userLoginDao;

    @Override
    public UserDetails loadUserByUsername(String username) {
        LOGGER.debug("Loading user by username: {}", username);
        UserLogin userLogin = userLoginDao.loadUserLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with name: " + username));
        String permissions = userLogin.getPermissions();
        LOGGER.debug("Loaded user {} with permissions: ", userLogin, permissions);
        final List<GrantedAuthority> grantedAuthorities =
                AuthorityUtils.commaSeparatedStringToAuthorityList(permissions);
        return new User(userLogin.getUsername(), userLogin.getPassword(), grantedAuthorities);
    }

    public void setUserLoginDao(UserLoginDao userLoginDao) {
        this.userLoginDao = userLoginDao;
    }
}
