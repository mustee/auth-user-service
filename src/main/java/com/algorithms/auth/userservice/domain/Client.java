package com.algorithms.auth.userservice.domain;

import com.algorithms.auth.userservice.domain.enums.ClientType;
import com.algorithms.auth.userservice.domain.draft.ClientDraft;

import java.util.Date;

public class Client extends ClientDraft {
    private final long id;


    public Client(long id, long accountId, String name, String clientId, String clientSecret, ClientType clientType, long jwtExpiration,
                  boolean useUsernamePasswordAuthentication, Date dateAdded, String description){
        super(accountId, name, clientId, clientSecret, clientType, jwtExpiration, useUsernamePasswordAuthentication, dateAdded, description);
        this.id = id;
    }

    public Client(long id, ClientDraft draft){
        this(id, draft.getAccountId(), draft.getName(), draft.getClientId(), draft.getClientSecret(), draft.getClientType(),
                draft.getJwtExpiration(), draft.isUseUsernamePasswordAuthentication(), draft.getDateAdded(), draft.getDescription());
    }

    public long getId() {
        return id;
    }

    @Override
    public Builder draft() {
        return new Builder(this);
    }
}
