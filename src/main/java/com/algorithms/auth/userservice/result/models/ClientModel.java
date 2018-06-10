package com.algorithms.auth.userservice.result.models;

import com.algorithms.auth.userservice.domain.Client;
import com.algorithms.auth.userservice.domain.enums.ClientType;

public class ClientModel {
    private final long id;
    private final String name;
    private final String clientId;
    private final String clientSecret;
    private final ClientType clientType;
    private final long jwtExpiration;
    private final boolean useUsernamePasswordAuthentication;
    private final String description;

    public ClientModel(long id, String name, String clientId, String clientSecret, ClientType clientType, long jwtExpiration,
                       boolean useUsernamePasswordAuthentication, String description) {
        this.id = id;
        this.name = name;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.clientType = clientType;
        this.jwtExpiration = jwtExpiration;
        this.useUsernamePasswordAuthentication = useUsernamePasswordAuthentication;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public long getJwtExpiration() {
        return jwtExpiration;
    }

    public boolean isUseUsernamePasswordAuthentication() {
        return useUsernamePasswordAuthentication;
    }

    public String getDescription() {
        return description;
    }


    public static ClientModel from(Client client){
        return new ClientModel(client.getId(), client.getName(), client.getClientId(), client.getClientSecret(), client.getClientType(),
                client.getJwtExpiration(), client.isUseUsernamePasswordAuthentication(), client.getDescription());
    }
}
