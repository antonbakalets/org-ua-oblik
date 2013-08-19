package org.ua.oblik.security;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.ua.oblik.domain.dao.UserLoginDao;
import org.ua.oblik.domain.dao.UserNotFoundException;
import org.ua.oblik.domain.model.UserLogin;

/**
 *
 * @author Anton Bakalets
 */
public class OblikUserDetailsService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(OblikUserDetailsService.class);

    private UserLoginDao userLoginDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserLogin userLogin;
        try {
            LOG.debug("Loading user by username: {}", username);
            userLogin = userLoginDao.loadUserLogin(username);
        } catch (UserNotFoundException unfe) {
            LOG.warn("Cannot find user by name.", unfe);
            throw new UsernameNotFoundException("No user with name: " + username);
        }
        final String permissions = userLogin.getPermissions();
        LOG.debug("Loaded user {} with permissions: ", userLogin, permissions);
        final List<GrantedAuthority> grantedAuthorities =
                AuthorityUtils.commaSeparatedStringToAuthorityList(permissions);
        return new User(userLogin.getUsername(), userLogin.getPassword(), grantedAuthorities);
    }

    public UserLoginDao getUserLoginDao() {
        return userLoginDao;
    }

    public void setUserLoginDao(UserLoginDao userLoginDao) {
        this.userLoginDao = userLoginDao;
    }
}
