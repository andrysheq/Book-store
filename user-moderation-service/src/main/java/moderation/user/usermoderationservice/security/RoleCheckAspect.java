package moderation.user.usermoderationservice.security;

import com.example.library.exception.ForbiddenException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * AOP Aspect для проверки JWT токена и ролей
 * Перехватывает методы, помеченные @RequireRole
 */
@Aspect
@Component
@RequiredArgsConstructor
public class RoleCheckAspect {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * Перехватить методы с аннотацией @RequireRole
     * и проверить наличие требуемой роли
     */
    @Before("@annotation(requireRole)")
    public void checkRole(JoinPoint joinPoint, RequireRole requireRole) {
        // Получить токен из заголовка Authorization
        String token = extractTokenFromRequest();

        // Проверить наличие токена
        if (token == null) {
            throw new ForbiddenException(
                    "Отсутствует токен в заголовке Authorization",
                    "MISSING_TOKEN"
            );
        }

        // Валидировать токен
        if (!jwtTokenProvider.validateToken(token)) {
            throw new ForbiddenException(
                    "Невалидный токен",
                    "INVALID_TOKEN"
            );
        }

        // Получить требуемую роль из аннотации
        String requiredRole = requireRole.value();

        // Проверить наличие роли в токене
        if (!jwtTokenProvider.hasRole(token, requiredRole)) {
            String username = jwtTokenProvider.getUsernameFromToken(token);
            throw new ForbiddenException(
                    "Пользователь " + username + " не имеет роль " + requiredRole,
                    "INSUFFICIENT_PERMISSIONS"
            );
        }
    }

    /**
     * Извлечь JWT токен из заголовка Authorization
     * Ожидается формат: "Bearer <token>"
     */
    private String extractTokenFromRequest() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return null;
            }

            HttpServletRequest request = attributes.getRequest();
            String authHeader = request.getHeader(AUTHORIZATION_HEADER);

            if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
                return authHeader.substring(BEARER_PREFIX.length());
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
