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

/**
 * Filtre d'authentification JWT qui intercepte toutes les requêtes HTTP.
 * Vérifie la présence et la validité d'un token JWT dans l'en-tête Authorization.
 * Certains chemins sont exemptés de l'authentification comme l'enregistrement,
 * la réauthentification, la validation de token et les endpoints Swagger.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    /**
     * Client REST utilisé pour valider les tokens en appelant le service d'authentification.
     */
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Traite chaque requête HTTP entrante et applique la logique d'authentification.
     * Les requêtes OPTIONS et certains chemins spécifiques sont exemptés de la vérification.
     * Pour les autres requêtes, vérifie la présence d'un token JWT valide dans l'en-tête.
     * <p>
     * Le processus d'authentification suit ces étapes :
     * 1. Vérification du type de requête et des chemins exemptés
     * 2. Extraction du token de l'en-tête Authorization
     * 3. Validation du token via l'endpoint /auth/validate
     * 4. Création d'un objet Authentication pour Spring Security si le token est valide
     *
     * @param request     La requête HTTP entrante
     * @param response    La réponse HTTP à renvoyer
     * @param filterChain La chaîne de filtres à poursuivre
     * @throws ServletException Si une erreur de servlet se produit
     * @throws IOException      Si une erreur d'entrée/sortie se produit
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String path = request.getRequestURI();

        if (path.startsWith(request.getContextPath() + "/auth/register") ||
                path.startsWith(request.getContextPath() + "/auth/re-authenticate") ||
                path.startsWith(request.getContextPath() + "/auth/validate") ||
                (path.contains("/v3/api-docs") || path.contains("/swagger-ui") || path.contains("/index.html"))
        ) {
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
                return;
            }
        } catch (HttpClientErrorException e) {
            response.setStatus(e.getStatusCode().value());
            response.getWriter().write(e.getStatusText());
            return;
        }

        filterChain.doFilter(request, response);
    }
}