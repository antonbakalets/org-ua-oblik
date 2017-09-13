package org.ua.oblik.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public final class SecurityService {

    private SecurityService() {
        // utility class
    }

    public static UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getUsername() {
        return getUserDetails().getUsername();
    }
}
