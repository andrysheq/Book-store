package com.example.authenticationservice.services.authorize;


import com.example.authenticationservice.dto.AccountDto;
import com.example.authenticationservice.dto.JwtDto;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public interface IAuthorizeService {

    void register(AccountDto accountDto);

    JwtDto login(AccountDto accountDto);

    JwtDto refresh(JwtDto jwtDto);
}
