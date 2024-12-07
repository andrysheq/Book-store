package com.example.authenticationservice.mappers;

import com.example.authenticationservice.dto.AccountDto;
import com.example.authenticationservice.entites.Account;
import org.keycloak.representations.idm.UserRepresentation;

public interface UserRepresentationMapper {

    AccountDto representToDto(UserRepresentation userRepresentation);

    UserRepresentation dtoToRepresent(AccountDto accountDto);

    Account representToAccount(UserRepresentation userRepresentation);

    UserRepresentation accountToRepresent(Account account);
}
