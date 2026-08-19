package com.sankalp.library.service;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class JwtService {

    private final Key signingKey;
    private final Duration expirationTime;

    public JwtService(Key signingKey, Duration expirationTime) {
        this.signingKey = signingKey;
        this.expirationTime = expirationTime;
    }

    public String createToken(Authentication authentication) {
        String sub = authentication.getName();

        Instant issuedAt = Instant.now();
        Instant expTime = issuedAt.plus(expirationTime);

        return Jwts.builder()
                .subject(sub)
                .expiration(Date.from(expTime))
                .signWith(signingKey)
                .compact();
    }
}
