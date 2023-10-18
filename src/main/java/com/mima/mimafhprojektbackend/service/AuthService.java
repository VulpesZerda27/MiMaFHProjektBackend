package com.mima.mimafhprojektbackend.service;

import com.mima.mimafhprojektbackend.model.LoginResponse;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.security.JwtIssuer;
import com.mima.mimafhprojektbackend.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtIssuer jwtIssuer;
    public LoginResponse authenticateLogin(String email, String password){
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal)authentication.getPrincipal();

        var roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        var token = jwtIssuer.issue(principal.getUserId(), principal.getEmail(), roles, principal.isEnabled());

        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }

    public void invalidateSessionToken() {
        SecurityContextHolder.clearContext();
    }

    public boolean isLoggedInUser(MyUser user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUserId().equals(user.getId());
    }

    public boolean isLoggedInUserOrAdmin(MyUser user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        boolean isAdmin = false;

        for (GrantedAuthority authority:
                userPrincipal.getAuthorities()) {
            if(Objects.equals(authority.getAuthority(), "ADMIN")) isAdmin = true;
        }

        return userPrincipal.getUserId().equals(user.getId()) || isAdmin;
    }
}

