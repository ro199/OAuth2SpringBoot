package com.krugerChallenge.authorizationserver.service;

import com.krugerChallenge.authorizationserver.dto.ClientDto;
import com.krugerChallenge.authorizationserver.dto.MessageDto;
import com.krugerChallenge.authorizationserver.entity.Client;
import com.krugerChallenge.authorizationserver.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService implements RegisteredClientRepository {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public MessageDto create(ClientDto dto){
        Client client = clientFromDto(dto);
        clientRepository.save(client);
        return new MessageDto("client "+ client.getClientId() + " saved");
    }

    private Client clientFromDto(ClientDto dto){
        Client client = Client.builder()
                .clientId(dto.getClientId())
                .clientSecret(passwordEncoder.encode(dto.getClientSecret()))
                .authenticationMethods(dto.getAuthenticationMethods())
                .authorizationGrantTypes(dto.getAuthorizationGrantTypes())
                .redirectUris(dto.getRedirectUris())
                .scopes(dto.getScopes())
                .requireProofKey(dto.isRequireProofKey())
                .build();
        return client;
    }

    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        Client client = clientRepository.findByClientId(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        return Client.toRegisteredClient(client);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        return Client.toRegisteredClient(client);
    }


}
