package com.example.authenticationservice.services.keycloak.impls;

import com.example.authenticationservice.config.keycloak.KeyCloakManager;
import com.example.authenticationservice.services.keycloak.IKeyCloakService;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeyCloakServiceImpl implements IKeyCloakService {
    private final KeyCloakManager keyCloakManager;

    public KeyCloakServiceImpl(KeyCloakManager keyCloakManager) {
        this.keyCloakManager = keyCloakManager;
    }


    @Override
    public Integer createUser(UserRepresentation userRepresentation) {

        return keyCloakManager.getKeyCloakInstanceWithRealm().users().create(userRepresentation).getStatus();
    }

    @Override
    public List<UserRepresentation> readUserByUsername(String username) {
        return keyCloakManager.getKeyCloakInstanceWithRealm().users().searchByUsername(username, true);
    }


    @Override
    public List<UserRepresentation> readUsers(List<String> authIds) {

        return authIds.stream().map(authId -> {
            UserResource usersResource = keyCloakManager.getKeyCloakInstanceWithRealm().users().get(authId);
            return usersResource.toRepresentation();
        }).collect(Collectors.toList());
    }


    @Override
    public UserRepresentation readUser(String authId) {
        UsersResource userResource = keyCloakManager.getKeyCloakInstanceWithRealm().users();
        return userResource.get(authId).toRepresentation();
    }


    @Override
    public void updateUser(UserRepresentation userRepresentation) {
        keyCloakManager.getKeyCloakInstanceWithRealm().users()
                .get(userRepresentation.getId()).update(userRepresentation);
    }


}
