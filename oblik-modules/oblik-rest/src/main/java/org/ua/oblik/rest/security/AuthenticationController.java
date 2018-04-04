package org.ua.oblik.rest.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ua.oblik.rest.v1.dto.AuthenticationRequest;
import org.ua.oblik.rest.v1.dto.AuthenticationResponse;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public AuthenticationResponse auth(@RequestBody AuthenticationRequest authenticationRequest) {
        LOGGER.debug("Serving authentication request");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        return authenticationService.createAuthenticationToken(authentication, authenticationRequest.getUsername());
    }

//    @PostMapping("/register")
//    public ResponseEntity register() {

//    }

//    @PostMapping("/confirm")
//    public ResponseEntity confirm() {
//
//    }
}
