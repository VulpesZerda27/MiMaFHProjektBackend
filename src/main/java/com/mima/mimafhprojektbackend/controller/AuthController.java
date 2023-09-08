package com.mima.mimafhprojektbackend.controller;

import com.mima.mimafhprojektbackend.security.JwtIssuer;
import com.mima.mimafhprojektbackend.security.UserPrincipal;
import com.mima.mimafhprojektbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.mima.mimafhprojektbackend.model.LoginRequest;
import com.mima.mimafhprojektbackend.model.LoginResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        return authService.authenticateLogin(request.getEmail(), request.getPassword());
    }

    @GetMapping("/securityTest")
    public String securityTest(){
        return "It worked!";
    }
}
