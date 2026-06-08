package com.TuBes.HewanKu;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final TokenBlacklistRepository tokenBlacklistRepository;

    public JwtAuthenticationFilter(JWTUtil jwtUtil, TokenBlacklistRepository tokenBlacklistRepository) {
        this.jwtUtil = jwtUtil;
        this.tokenBlacklistRepository = tokenBlacklistRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        if (path.startsWith("/shelter/auth/") || path.startsWith("/pengguna/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null ||
                (!authHeader.startsWith("Bearer ") && !authHeader.startsWith("Access-Token "))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Missing or invalid Authorization header");
            return;
        }

        String token;
        if (authHeader.startsWith("Bearer ")) {
            token = authHeader.substring("Bearer ".length());
        } else {
            token = authHeader.substring("Access-Token ".length());
        }

        if (tokenBlacklistRepository.existsByToken(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Sesi telah berakhir (Logged Out). Silakan login kembali.");
            return;
        }

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtUtil.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        Long userId = Long.parseLong(claims.getSubject());
        String role = claims.get("role", String.class);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userId,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_" + role)));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
