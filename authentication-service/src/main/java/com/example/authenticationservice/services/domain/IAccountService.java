package org.example.services.domain;

import org.example.dto.AccountDto;
import org.springframework.stereotype.Service;

@Service
public interface IAccountService {

    void delete(Long id);

    void update(Long id, AccountDto accountDto);

    void create(AccountDto accountDto, String authId);

    AccountDto getOne(Long id);

    AccountDto getOne(String username);
}
