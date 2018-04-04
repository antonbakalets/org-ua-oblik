package org.ua.oblik.rest.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ua.oblik.rest.v1.dto.AuthenticationRequest;
import org.ua.oblik.rest.v1.dto.AuthenticationResponse;
import org.ua.oblik.rest.v1.security.AuthenticationService;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/v1/auth")
    public AuthenticationResponse auth(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        return authenticationService.createAuthenticationToken(authentication, authenticationRequest.getUsername());
    }
}
