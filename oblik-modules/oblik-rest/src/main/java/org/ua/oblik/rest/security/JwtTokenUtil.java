package org.ua.oblik.rest.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Utilities for dealing with JWT Tokens
 */
@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -1393917125390421429L;

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${oblik.security.secret}")
    private String secret;

    @Value("${oblik.security.expiration}")
    private Long expiration;

    public Optional<String> getUsernameFromToken(String token) {
        return getClaimsFromToken(token).map(Claims::getSubject);
    }

    public Optional<Date> getCreatedDateFromToken(String token) {
        return getClaimsFromToken(token).map(claims -> new Date((Long) claims.get(CLAIM_KEY_CREATED)));
    }

    public Optional<Date> getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).map(Claims::getExpiration);
    }

    private Optional<Claims> getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return Optional.ofNullable(claims);
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    public Boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token)
                .map(date -> date.before(new Date()))
                .orElse(false);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Optional<String> refreshToken(String token) {
        return getClaimsFromToken(token).map(claims -> {
            claims.put(CLAIM_KEY_CREATED, new Date());
            return generateToken(claims);
        });
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return getUsernameFromToken(token)
                .map(username -> username.equals(userDetails.getUsername()) && !isTokenExpired(token))
                .orElse(false);
    }
}