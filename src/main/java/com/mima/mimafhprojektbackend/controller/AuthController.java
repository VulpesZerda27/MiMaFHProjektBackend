package com.mima.mimafhprojektbackend.controller;

import com.mima.mimafhprojektbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.mima.mimafhprojektbackend.model.LoginRequest;
import com.mima.mimafhprojektbackend.model.LoginResponse;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        return authService.authenticateLogin(request.getEmail(), request.getPassword());
    }

    @GetMapping("/auth/logout")
    public ResponseEntity<String> logout(AuthService authService) {
        authService.invalidateSessionToken();
        return ResponseEntity.noContent().build();
    }
}