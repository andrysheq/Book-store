package com.example.library.usersservice.security;

import com.example.library.usersservice.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;
import io.jsonwebtoken.Claims;


/**
 * JWT Token Provider - генерирует и парсит JWT токены
 */
@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret:your-secret-key-change-this}")
    private String jwtSecret;

    @Value("${app.jwt.expiration:86400000}")
    private long jwtExpirationMs;

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

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
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
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
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
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
