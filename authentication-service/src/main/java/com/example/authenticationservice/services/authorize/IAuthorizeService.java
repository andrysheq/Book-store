package org.example.services.authorize;

import org.example.dto.AccountDto;
import org.example.dto.JwtDto;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public interface IAuthorizeService {

    void register(AccountDto accountDto);

    JwtDto login(AccountDto accountDto);

    JwtDto refresh(JwtDto jwtDto);
}
