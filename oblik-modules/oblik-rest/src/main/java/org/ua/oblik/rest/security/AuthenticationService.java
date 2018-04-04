package org.ua.oblik.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.ua.oblik.rest.v1.dto.AuthenticationResponse;

@Service
public class AuthenticationService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public AuthenticationResponse createAuthenticationToken(Authentication authentication, String username) {
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username); // throws UsernameNotFoundException
        final String token = jwtTokenUtil.generateToken(userDetails);

        return new AuthenticationResponse(token,
                jwtTokenUtil.getExpirationDateFromToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Generated token cannot be empty.")));
    }
}
