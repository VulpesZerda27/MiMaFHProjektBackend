package com.mima.mimafhprojektbackend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Component
@RequiredArgsConstructor
public class JwtIssuer {
    private final JwtProperties jwtProperties;
    public String Issue(Long userId, String email, List<String> roles){
        Key key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());

        return Jwts.builder()
                .claim("id", userId)
                .claim("email", email)
                .claim("roles", roles)
                .setExpiration(Date.from(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS))))
                .signWith(key, HS256)
                .compact();
    }
}
