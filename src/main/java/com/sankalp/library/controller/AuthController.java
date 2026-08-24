package com.sankalp.library.controller;

import com.sankalp.library.dto.AuthResponse;
import com.sankalp.library.dto.AuthResult;
import com.sankalp.library.dto.LoginRequest;
import com.sankalp.library.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {

        AuthResult result = authService.login(loginRequest);

        ResponseCookie refreshCookie = ResponseCookie
                .from("__Host-refresh-token", result.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofDays(7))
                .build();

        response.addHeader("Set-Cookie", refreshCookie.toString());

        return ResponseEntity.ok(new AuthResponse(result.getAccessToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@CookieValue(value = "__Host-refresh-token", required = false) String refreshToken, HttpServletResponse response) {

        AuthResult result = authService.refresh(refreshToken);

        ResponseCookie refreshCookie = ResponseCookie
                .from("__Host-refresh-token", result.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofDays(7))
                .build();

        response.addHeader("Set-Cookie", refreshCookie.toString());

        return ResponseEntity.ok(new AuthResponse(result.getAccessToken()));
    }
}
