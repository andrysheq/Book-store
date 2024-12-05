package org.example.services.authorize.impls;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.example.dto.AccountDto;
import org.example.dto.JwtDto;
import org.example.exceptions.AlreadyExistsException;
import org.example.mappers.UserRepresentationMapper;
import org.example.services.authorize.IAuthorizeService;
import org.example.services.domain.IAccountService;
import org.example.services.keycloak.IKeyCloakService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class AuthorizeService implements IAuthorizeService {

    private final IAccountService accountService;

    private final IKeyCloakService keyCloakService;

    private final UserRepresentationMapper userRepresentationMapper;

    @Value("${app.config.keycloak.grant_type_login}")
    private String loginGrantType;

    @Value("${app.config.keycloak.grant_type_refresh}")
    private String refreshGrantType;

    @Value("${app.config.keycloak.scope}")
    private String scope;

    @Qualifier("keycloakWebClient")
    private final WebClient webClient;

    @Value("${app.config.keycloak.realm}")
    private String realm;

    @Value("${app.config.keycloak.client-id}")
    private String clientId;

    @Value("${app.config.keycloak.client-secret}")
    private String clientSecret;

    public AuthorizeService(IAccountService accountService,
                            IKeyCloakService keyCloakService,
                            UserRepresentationMapper userRepresentationMapper,
                            WebClient webClient) {
        this.accountService = accountService;
        this.keyCloakService = keyCloakService;
        this.userRepresentationMapper = userRepresentationMapper;
        this.webClient = webClient;
    }

    @Override
    public void register(AccountDto accountDto) {
        log.info(accountDto.toString());
        List<UserRepresentation> userRepresentations = keyCloakService.readUserByUsername(accountDto.getUsername());
        if (!userRepresentations.isEmpty()) {
            throw new AlreadyExistsException("Аккаунт с такими данными существует");
        }
        UserRepresentation userRepresentation = userRepresentationMapper.dtoToRepresent(accountDto);

        Integer userCreationResponse = keyCloakService.createUser(userRepresentation);
        log.info(userCreationResponse.toString());
        if (userCreationResponse.equals(201)) {

            List<UserRepresentation> representations = keyCloakService.readUserByUsername(accountDto.getUsername());

            accountService.create(accountDto, representations.get(0).getId());
        } else {
            throw new RuntimeException("User with identification number not found");
        }
    }

    @Override
    public JwtDto login(AccountDto accountDto) {
        return webClient.post().
                contentType(MediaType.APPLICATION_FORM_URLENCODED).
                body(BodyInserters.fromFormData("client_id", clientId).
                        with("client_secret", clientSecret).
                        with("username", accountDto.getUsername()).
                        with("password", accountDto.getPassword()).
                        with("scope", scope).
                        with("grant_type", loginGrantType)).
                retrieve().
                bodyToMono(JwtDto.class).
                block();
    }

    @Override
    public JwtDto refresh(JwtDto jwtDto) {
        return webClient.post().
                contentType(MediaType.APPLICATION_FORM_URLENCODED).body(
                        BodyInserters.fromFormData("client_id", clientId).
                                with("client_secret", clientSecret).
                                with("refresh_token", jwtDto.getRefresh_token()).
                                with("scope", scope).
                                with("grant_type", refreshGrantType)).
                retrieve().
                bodyToMono(JwtDto.class).
                block();
    }
}
