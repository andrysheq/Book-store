package com.example.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable).authorizeExchange(request ->
                        request.pathMatchers("/auth/**").permitAll().
                                pathMatchers("/flats/**").authenticated()).
                oauth2Login(Customizer.withDefaults()).
                oauth2ResourceServer(resource->resource.jwt(Customizer.withDefaults()));
        return http.build();
    }


}
