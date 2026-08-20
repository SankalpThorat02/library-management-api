package com.sankalp.library.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final Duration expirationTime;

    public JwtService(SecretKey signingKey, Duration expirationTime) {
        this.signingKey = signingKey;
        this.expirationTime = expirationTime;
    }

    public String createToken(Authentication authentication) {
        String sub = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        Instant issuedAt = Instant.now();
        Instant expTime = issuedAt.plus(expirationTime);

        return Jwts.builder()
                .subject(sub)
                .expiration(Date.from(expTime))
                .claim("authorities", authorities)
                .signWith(signingKey)
                .compact();
    }

    public Jws<Claims> parseAndValidateToken(String jwt) throws JwtException {

        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(jwt);
    }
}
