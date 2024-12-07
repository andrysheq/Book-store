package com.example.authenticationservice.controllers;


import com.example.authenticationservice.dto.AccountDto;
import com.example.authenticationservice.dto.JwtDto;
import com.example.authenticationservice.services.authorize.IAuthorizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class AuthController {

    private final IAuthorizeService authorizeService;

    public AuthController(IAuthorizeService authorizeService) {
        this.authorizeService = authorizeService;
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody AccountDto accountDto) {
        authorizeService.register(accountDto);
        return ResponseEntity.ok("Аккаунт успешно создан");
    }

    @PostMapping("login")
    public ResponseEntity<JwtDto> login(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(authorizeService.login(accountDto));
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtDto> refresh(@RequestBody JwtDto jwtDto){
        return ResponseEntity.ok(authorizeService.refresh(jwtDto));
    }

}
