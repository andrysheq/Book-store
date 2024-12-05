package org.example.mappers.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.AccountDto;
import org.example.entites.Account;
import org.example.mappers.UserRepresentationMapper;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class UserRepresentationMapperImpl implements UserRepresentationMapper {
    @Override
    public AccountDto representToDto(UserRepresentation userRepresentation) {
        return AccountDto.builder().
                email(userRepresentation.getEmail()).
                username(userRepresentation.getUsername()).
                firstname(userRepresentation.getFirstName()).
                lastname(userRepresentation.getLastName())
                .build();
    }

    @Override
    public UserRepresentation dtoToRepresent(AccountDto accountDto) {
        UserRepresentation userRepresentation = new UserRepresentation();
        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setValue(accountDto.getPassword());
        credentials.setTemporary(false);
        userRepresentation.setEmail(accountDto.getEmail());
        userRepresentation.setFirstName(accountDto.getFirstname());
        userRepresentation.setLastName(accountDto.getLastname());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(accountDto.getUsername());
        userRepresentation.setCredentials(List.of(credentials));
        log.info(accountDto.toString());
        return userRepresentation;
    }

    @Override
    public Account representToAccount(UserRepresentation userRepresentation) {
        return Account.builder().
                authId(userRepresentation.getId()).
                email(userRepresentation.getEmail()).
                username(userRepresentation.getUsername()).
                firstname(userRepresentation.getFirstName()).
                isLocked(userRepresentation.isEnabled()).
                password(userRepresentation.getCredentials().get(0).getValue()).
                lastname(userRepresentation.getLastName())
                .build();
    }

    @Override
    public UserRepresentation accountToRepresent(Account account) {
        UserRepresentation userRepresentation = new UserRepresentation();
        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setValue(account.getPassword());
        credentials.setTemporary(false);
        userRepresentation.setEmail(account.getEmail());
        userRepresentation.setFirstName(account.getFirstname());
        userRepresentation.setLastName(account.getLastname());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(account.getIsLocked());
        userRepresentation.setUsername(account.getUsername());
        userRepresentation.setCredentials(List.of(credentials));
        return userRepresentation;
    }
}
