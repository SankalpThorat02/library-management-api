package com.sankalp.library.filter;

import com.sankalp.library.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public  JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String authorization = request.getHeader("Authorization");

            if (authorization != null && authorization.startsWith("Bearer ")) {
                String token = authorization.substring(7);

                Jws<Claims> jws = jwtService.parseAndValidateToken(token);
                Claims claims = jws.getPayload();

                String username = claims.getSubject();
                List<String> authorities = claims.get("authorities", List.class);

                List<GrantedAuthority> grantedAuthorities = authorities
                        .stream()
                        .map(auth -> new SimpleGrantedAuthority(auth))
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken tokenAuthentication = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);

                SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
            }

            filterChain.doFilter(request, response);

    }
}
