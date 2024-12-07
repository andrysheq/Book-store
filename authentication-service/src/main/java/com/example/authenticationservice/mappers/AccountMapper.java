package com.example.authenticationservice.mappers;

import com.example.authenticationservice.dto.AccountDto;
import com.example.authenticationservice.entites.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "SPRING")
public interface AccountMapper {

    AccountDto accToDto(Account account);

    Account dtoToAccount(AccountDto accountDto);
}
