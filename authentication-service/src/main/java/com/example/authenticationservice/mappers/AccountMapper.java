package org.example.mappers;

import org.example.dto.AccountDto;
import org.example.entites.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "SPRING")
public interface AccountMapper {

    AccountDto accToDto(Account account);

    Account dtoToAccount(AccountDto accountDto);
}
