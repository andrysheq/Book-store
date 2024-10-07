package com.andrysheq.authservice.controller;

import com.andrysheq.authservice.domain.JwtAuthenticationResponse;
import com.andrysheq.authservice.domain.SignInRequest;
import com.andrysheq.authservice.domain.SignUpRequest;
import com.andrysheq.authservice.security.service.AuthenticationService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
//@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthenticationService authenticationService;

    //@Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody SignUpRequest request) {//@Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    //@Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody SignInRequest request) {//@Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}
