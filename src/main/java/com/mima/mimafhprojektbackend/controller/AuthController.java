package com.mima.mimafhprojektbackend.controller;

import com.mima.mimafhprojektbackend.security.JwtIssuer;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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
    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request){
        var token = jwtIssuer.Issue(1L, request.getEmail(), List.of("USER"));
        return LoginResponse.builder()
                .accessToken("Test")
                .build();
    }
}
