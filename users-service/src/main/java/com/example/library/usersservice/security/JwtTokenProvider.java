package com.example.library.usersservice.security;

import com.example.library.usersservice.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * JWT Token Provider - генерирует и парсит JWT токены
 * ✅ ОБНОВЛЕНО для JJWT 0.12.3
 */
@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret:sjBTLz9GBJbugnMAhVFg5JUOmzNHwYaECfP89ocwX/8=}")
    private String jwtSecret;

    @Value("${app.jwt.expiration:315360000000}")
    private long jwtExpirationMs;

    /**
     * Получить SecretKey для подписи
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Генерировать JWT токен для пользователя
     */
    public String generateToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", List.of(user.getRole()));
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());

        return createToken(claims, user.getEmail());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Получить Claims из токена
     */
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Получить username (email) из токена
     */
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * Получить роли из токена
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
     * Проверить роль
     */
    public boolean hasRole(String token, String requiredRole) {
        return getRolesFromToken(token).stream()
                .anyMatch(role -> role.equalsIgnoreCase(requiredRole));
    }

    /**
     * Проверить любую из ролей
     */
    public boolean hasAnyRole(String token, String... requiredRoles) {
        return getRolesFromToken(token).stream()
                .anyMatch(role -> Arrays.stream(requiredRoles)
                        .anyMatch(requiredRole -> role.equalsIgnoreCase(requiredRole)));
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

    /**
     * Получить время истечения токена
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    /**
     * Проверить, истёк ли токен
     */
    public boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }
}
