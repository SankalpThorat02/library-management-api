package com.sankalp.library.service;

import com.sankalp.library.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public  RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }
}
