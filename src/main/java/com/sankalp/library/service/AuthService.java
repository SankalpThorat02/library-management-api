package com.sankalp.library.service;

import com.sankalp.library.dto.AuthResponse;
import com.sankalp.library.dto.LoginRequest;
import com.sankalp.library.entity.RefreshToken;
import com.sankalp.library.entity.User;
import com.sankalp.library.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    public AuthResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManager.authenticate(token);

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() ->
                        new UsernameNotFoundException("Username not found"));

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        String accessToken = jwtService.createToken(authentication);

        return new AuthResponse(accessToken, refreshToken.getToken());

    }
}
