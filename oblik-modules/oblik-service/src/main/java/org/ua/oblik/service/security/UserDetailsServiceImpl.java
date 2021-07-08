package org.ua.oblik.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.ua.oblik.domain.dao.UserLoginRepository;
import org.ua.oblik.domain.model.UserLogin;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        LOGGER.debug("Loading user by username: {}", username);
        UserLogin userLogin = userLoginRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with name: " + username));
        String permissions = userLogin.getPermissions();
        LOGGER.debug("Loaded user {} with permissions: {}", userLogin, permissions);
        final List<GrantedAuthority> grantedAuthorities =
                AuthorityUtils.commaSeparatedStringToAuthorityList(permissions);
        return new User(userLogin.getUsername(), userLogin.getPassword(), grantedAuthorities);
    }
}
