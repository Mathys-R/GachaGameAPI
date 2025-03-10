package com.imt.GachaGameAPI.auth.security;

import com.imt.GachaGameAPI.auth.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final List<String> AUTH_WHITELIST = Arrays.asList(
            "/auth/login",
            "/auth/register",
            "/auth/validate",
            "/v3/api-docs/**", 
            "/swagger-ui/**",
            "/swagger-ui.html"
    );
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (AUTH_WHITELIST.stream().anyMatch(path::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendErrorResponse(response, "Token d'authentification absent ou invalide");
            return;
        }

        String token = authHeader.substring(7);

        var userOpt = userService.findUserByToken(token);
        if (userOpt.isEmpty()) {
            sendErrorResponse(response, "Token inexistant");
            return;
        }

        var user = userOpt.get();
        if (!user.isTokenValid()) {
            sendErrorResponse(response, "Token expiré");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"Erreur\": \"" + message + "\"}");
    }
}