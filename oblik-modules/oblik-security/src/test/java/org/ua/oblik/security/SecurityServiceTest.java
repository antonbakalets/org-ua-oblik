package org.ua.oblik.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityServiceTest {

    private Authentication authentication;

    @Before
    public void setUp() {
        authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void testConstructor() throws Exception {
        Constructor constructor = SecurityService.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        SecurityService instance = (SecurityService)constructor.newInstance();
        assertNotNull(instance);
    }

    @Test
    public void testGetUserDetails() {
        UserDetails principal = mock(UserDetails.class);
        when(authentication.getPrincipal()).thenReturn(principal);
        UserDetails userDetails = SecurityService.getUserDetails();
        assertEquals("User details.", principal, userDetails);
    }

    @Test
    public void testGetUsername() {
        UserDetails principal = mock(UserDetails.class);
        when(principal.getUsername()).thenReturn("username");
        when(authentication.getPrincipal()).thenReturn(principal);
        String username = SecurityService.getUsername();
        assertEquals("User name.", "username", username);
    }
}
