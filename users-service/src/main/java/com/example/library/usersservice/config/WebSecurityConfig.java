package com.example.library.usersservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // Разрешить Swagger БЕЗ авторизации
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                // Разрешить Auth endpoints БЕЗ авторизации
                .requestMatchers("/auth/register", "/auth/login").permitAll()
                // Остальное требует авторизации
                .anyRequest().authenticated()
                .and()
                // Отключить HTTP Basic Auth (используем JWT)
                .httpBasic().disable()
                .formLogin().disable();

        return http.build();
    }
}

