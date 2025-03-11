package com.imt.GachaGameAPI.auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final RestTemplate restTemplate = new RestTemplate();
    private final List<String> publicPaths = Arrays.asList(
            "/auth/register",
            "/auth/re-authenticate",
            "/auth/validate",
            "/v3/api-docs",
            "/swagger-ui",
            "/swagger-ui.html"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String path = request.getRequestURI();

        if (isPublicPath(path, request.getContextPath())) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Header Authorization manquant ou invalide");
            return;
        }

        String token = authHeader.substring(7);
        validateTokenAndAuthenticate(token, response);

        filterChain.doFilter(request, response);
    }

    private boolean isPublicPath(String path, String contextPath) {
        return publicPaths.stream()
                .anyMatch(publicPath -> path.startsWith(contextPath + publicPath));
    }

    private void validateTokenAndAuthenticate(String token, HttpServletResponse response) throws IOException {
        try {
            ResponseEntity<String> authResponse =
                    restTemplate.getForEntity("http://api-auth:8081/auth/validate/" + token, String.class);

            if (authResponse.getStatusCode().is2xxSuccessful() && authResponse.getBody() != null) {
                String username = authResponse.getBody();
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Token invalide");
            }
        } catch (HttpClientErrorException e) {
            response.setStatus(e.getStatusCode().value());
            response.getWriter().write(e.getStatusText());
        }
    }
}