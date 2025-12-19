package com.example.library.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JWT Token Provider - извлекает информацию из JWT токена
 * ✅ ОБНОВЛЕНО для JJWT 0.12.3
 */
@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret:sjBTLz9GBJbugnMAhVFg5JUOmzNHwYaECfP89ocwX/8=}")
    private String jwtSecret;

    @Value("${app.jwt.expiration:315360000000}")
    private long jwtExpirationMs;

    /**
     * Получить SecretKey для парсинга
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Получить Claims из токена
     */
    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (SignatureException e) {
            throw new IllegalArgumentException("Invalid JWT signature", e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT token", e);
        }
    }


    /**
     * Получить username из токена
     */
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * Получить роли из токена (MODERATOR, ADMIN, USER)
     * Ожидается, что роли хранятся в claims с ключом "roles" как список строк
     */
    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        Claims claims = getClaimsFromToken(token);

        Object rolesObj = claims.get("roles");

        if (rolesObj instanceof Collection<?>) {
            return ((Collection<?>) rolesObj).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }

        if (rolesObj instanceof String) {
            return List.of((String) rolesObj);
        }

        return List.of();
    }

    /**
     * Проверить, что токен содержит конкретную роль
     */
    public boolean hasRole(String token, String requiredRole) {
        List<String> roles = getRolesFromToken(token);
        return roles.stream()
                .anyMatch(role -> role.equalsIgnoreCase(requiredRole));
    }

    /**
     * Проверить, что токен содержит одну из указанных ролей
     */
    public boolean hasAnyRole(String token, String... requiredRoles) {
        List<String> roles = getRolesFromToken(token);
        return roles.stream()
                .anyMatch(role -> {
                    for (String requiredRole : requiredRoles) {
                        if (role.equalsIgnoreCase(requiredRole)) {
                            return true;
                        }
                    }
                    return false;
                });
    }

    /**
     * Валидировать токен
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
