package org.example.services.keycloak;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IKeyCloakService {

    Integer createUser(UserRepresentation userRepresentation);

    List<UserRepresentation> readUserByUsername(String username);

    List<UserRepresentation> readUsers(List<String> authIds);

    UserRepresentation readUser(String authId);

    void updateUser(UserRepresentation userRepresentation);

}
