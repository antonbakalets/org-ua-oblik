package org.ua.oblik.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Anton Bakalets
 */
public class SecurityService {

    public static UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    
    public static String getUsername() {
        return getUserDetails().getUsername();
    }
}
