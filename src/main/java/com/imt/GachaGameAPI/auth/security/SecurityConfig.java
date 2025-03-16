package com.imt.GachaGameAPI.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * Configuration de sécurité pour l'API.
 * Configure les règles d'accès aux endpoints, la gestion CORS,
 * la désactivation de CSRF et l'authentification par token JWT.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Filtre d'authentification JWT qui vérifie la présence et la validité des tokens.
     */
    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    /**
     * Configure la chaîne de filtres de sécurité pour l'application.
     * Permet un accès public aux endpoints d'authentification et de documentation,
     * et exige une authentification pour tous les autres endpoints.
     *
     * @param http La configuration HttpSecurity à personnaliser
     * @return La chaîne de filtres de sécurité configurée
     * @throws Exception Si une erreur survient pendant la configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "/auth/re-authenticate", "/auth/validate/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/index.html").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Crée un filtre CORS pour gérer les requêtes cross-origin.
     * Configure les origines, méthodes et en-têtes autorisés.
     *
     * @return Un filtre CORS configuré
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // config.setAllowedOrigins(List.of("http://localhost:8080")); // Allow frontend
        config.setAllowedOrigins(List.of("http://localhost:8080", "http://localhost:8081", "http://localhost:8082", "http://localhost:8083", "http://localhost:8084"));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // Allow cookies & authentication
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /**
     * Crée une source de configuration CORS basée sur les URL.
     * Configure les origines, méthodes et en-têtes autorisés pour toutes les routes.
     *
     * @return Une source de configuration CORS
     */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // config.setAllowedOrigins(List.of("http://localhost:8080")); // Allow frontend
        config.setAllowedOrigins(List.of("http://localhost:8080", "http://localhost:8081", "http://localhost:8082", "http://localhost:8083", "http://localhost:8084"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}