package org.ua.oblik.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.ua.oblik.domain.dao.UserLoginDao;
import org.ua.oblik.domain.model.UserLogin;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OblikUserDetailsServiceTest {

    private static final String TEST_USER = "testUser";
    private static final String TEST_USER_WITH_PERMISSIONS = "testUserWithPermissions";
    private static final String PERMISSIONS = "read,write";


    @InjectMocks
    private OblikUserDetailsService oblikUserDetailsService;

    @Mock
    private UserLoginDao userLoginDao;

    @Before
    public void setUp() {
        UserLogin testUser = mock(UserLogin.class);
        when(testUser.getUsername()).thenReturn(TEST_USER);
        when(testUser.getPassword()).thenReturn(TEST_USER);
        when(testUser.getPermissions()).thenReturn("");
        when(userLoginDao.findByUsernameIgnoreCase(Matchers.eq(TEST_USER))).thenReturn(Optional.of(testUser));

        UserLogin testUserPermissions = mock(UserLogin.class);
        when(testUserPermissions.getUsername()).thenReturn(TEST_USER_WITH_PERMISSIONS);
        when(testUserPermissions.getPassword()).thenReturn(TEST_USER_WITH_PERMISSIONS);
        when(testUserPermissions.getPermissions()).thenReturn(PERMISSIONS);
        when(userLoginDao.findByUsernameIgnoreCase(Matchers.eq(TEST_USER_WITH_PERMISSIONS)))
                .thenReturn(Optional.of(testUserPermissions));
    }

    @Test
    public void testUserLoadPositive() {
        UserDetails testUser = oblikUserDetailsService.loadUserByUsername(TEST_USER);
        assertEquals(TEST_USER, testUser.getUsername());

        UserDetails testUserPerm = oblikUserDetailsService.loadUserByUsername(TEST_USER_WITH_PERMISSIONS);
        assertEquals(TEST_USER_WITH_PERMISSIONS, testUserPerm.getUsername());
        assertEquals(2, testUserPerm.getAuthorities().size());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testUserLoadNegative() {
        when(userLoginDao.findByUsernameIgnoreCase(Matchers.isNull(String.class))).thenReturn(Optional.empty());
        oblikUserDetailsService.loadUserByUsername(null);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testUserLoadUnknown() {
        when(userLoginDao.findByUsernameIgnoreCase(Matchers.eq("unknown"))).thenReturn(Optional.empty());
        oblikUserDetailsService.loadUserByUsername("unknown");
    }
}
