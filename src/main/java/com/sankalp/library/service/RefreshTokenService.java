package com.sankalp.library.service;

import com.sankalp.library.entity.RefreshToken;
import com.sankalp.library.entity.User;
import com.sankalp.library.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public  RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(User user) {

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(Duration.ofDays(7));

        SecureRandom secureRandom = new SecureRandom();

        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);

        String token = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setUser(user);
        refreshToken.setExpiresAt(expiresAt);
        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }
}
