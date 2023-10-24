package com.mima.mimafhprojektbackend.service;

import com.mima.mimafhprojektbackend.model.LoginResponse;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.security.JwtIssuer;
import com.mima.mimafhprojektbackend.security.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtIssuer jwtIssuer;

    private UserPrincipal userPrincipal;

    @BeforeEach
    void setUp() {
        userPrincipal = UserPrincipal.builder()
                .userId(1L)
                .email("test@test.com")
                .password("password")
                .authorities(List.of(new SimpleGrantedAuthority("USER")))
                .enabled(true)
                .build();

        Authentication mockAuthentication = mock(Authentication.class);
        lenient().when(mockAuthentication.getPrincipal()).thenReturn(userPrincipal);

        lenient().when(authenticationManager.authenticate(any())).thenReturn(mockAuthentication);
        lenient().when(jwtIssuer.issue(anyLong(), anyString(), anyList(), anyBoolean())).thenReturn("testToken");
    }

    @Test
    void testAuthenticateLogin() {
        LoginResponse loginResponse = authService.authenticateLogin("test@test.com", "password");
        assertEquals("testToken", loginResponse.getAccessToken());
    }

    @Test
    void testInvalidateSessionToken() {
        SecurityContextHolder.getContext().setAuthentication(mock(Authentication.class));
        authService.invalidateSessionToken();
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testIsLoggedInUser() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities()));
        MyUser user = new MyUser();
        user.setId(1L);
        assertTrue(authService.isLoggedInUser(user));
    }

    @Test
    void testIsLoggedInUserOrAdmin_User() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities()));
        MyUser user = new MyUser();
        user.setId(1L);
        assertTrue(authService.isLoggedInUserOrAdmin(user));
    }

    @Test
    void testIsLoggedInUserOrAdmin_Admin() {
        UserPrincipal adminPrincipal = UserPrincipal.builder()
                .userId(2L)
                .email("admin@test.com")
                .password("password")
                .authorities(List.of(new SimpleGrantedAuthority("ADMIN")))
                .enabled(true)
                .build();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(adminPrincipal, null, adminPrincipal.getAuthorities()));
        MyUser user = new MyUser();
        user.setId(2L);
        assertTrue(authService.isLoggedInUserOrAdmin(user));
    }
}

