package org.example.mappers;

import org.example.dto.AccountDto;
import org.example.entites.Account;
import org.keycloak.representations.idm.UserRepresentation;

public interface UserRepresentationMapper {

    AccountDto representToDto(UserRepresentation userRepresentation);

    UserRepresentation dtoToRepresent(AccountDto accountDto);

    Account representToAccount(UserRepresentation userRepresentation);

    UserRepresentation accountToRepresent(Account account);
}
