package com.mima.mimafhprojektbackend.security;

import com.mima.mimafhprojektbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.getUserByEmail(username).orElseThrow();
        var userRoles = user.getRoles();
        List<SimpleGrantedAuthority> userAuthorities = null;

        for (String role : userRoles) {
            userAuthorities.add(new SimpleGrantedAuthority(role));
        }

        return UserPrincipal.builder()
                .userId(user.getUserId())
                .email(user.getUserEmail())
                .authorities(userAuthorities)
                .password(user.getUserPassword())
                .build();
    }
}
