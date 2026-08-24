package com.sankalp.library.service;

import com.sankalp.library.dto.AuthResult;
import com.sankalp.library.dto.LoginRequest;
import com.sankalp.library.entity.RefreshToken;
import com.sankalp.library.entity.User;
import com.sankalp.library.exception.TokenNotFoundException;
import com.sankalp.library.repository.RefreshTokenRepository;
import com.sankalp.library.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository, RefreshTokenService refreshTokenService, RefreshTokenRepository refreshTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public AuthResult login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManager.authenticate(token);

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() ->
                        new UsernameNotFoundException("Username not found"));

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        String accessToken = jwtService.createToken(authentication);

        return new AuthResult(accessToken, refreshToken.getToken());
    }

    @Transactional
    public AuthResult refresh(String refreshToken) {

        RefreshToken token = refreshTokenService.validateRefreshToken(refreshToken);
        token.setRevoked(true);

        User user = token.getUser();
        RefreshToken newToken = refreshTokenService.createRefreshToken(user);

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), null, List.of(authority));

        String accessToken = jwtService.createToken(authToken);

        return new AuthResult(accessToken, newToken.getToken());
    }

    public void logout(String refreshToken) {

        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() ->
                        new TokenNotFoundException("Token not found"));

        token.setRevoked(false);

        refreshTokenRepository.save(token);
    }
}
