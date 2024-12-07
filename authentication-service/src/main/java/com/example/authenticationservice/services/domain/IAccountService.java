package com.example.authenticationservice.services.domain;


import com.example.authenticationservice.dto.AccountDto;
import org.springframework.stereotype.Service;

@Service
public interface IAccountService {

    void delete(Long id);

    void update(Long id, AccountDto accountDto);

    void create(AccountDto accountDto, String authId);

    AccountDto getOne(Long id);

    AccountDto getOne(String username);
}
