package com.algorithms.auth.userservice.domain.draft;

import com.algorithms.auth.userservice.domain.enums.ClientType;

import java.util.Date;

public class ClientDraft {
    protected final long accountId;
    protected final String name;
    protected final String clientId;
    protected final String clientSecret;
    protected final ClientType clientType;
    protected final long jwtExpiration;
    protected final boolean useUsernamePasswordAuthentication;
    protected final Date dateAdded;
    protected final String description;

    public ClientDraft(long accountId, String name, String clientId, String clientSecret, ClientType clientType, long jwtExpiration,
                  boolean useUsernamePasswordAuthentication, Date dateAdded, String description) {
        this.accountId = accountId;
        this.name = name;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.clientType = clientType;
        this.jwtExpiration = jwtExpiration;
        this.useUsernamePasswordAuthentication = useUsernamePasswordAuthentication;
        this.dateAdded = dateAdded;
        this.description = description;
    }

    public long getAccountId() {
        return accountId;
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

    public Date getDateAdded() {
        return dateAdded;
    }

    public String getDescription() {
        return description;
    }

    public Builder draft() {
        return new Builder(this);
    }

    public static class Builder {
        protected long accountId;
        protected String name;
        protected String clientId;
        protected String clientSecret;
        protected ClientType clientType;
        protected long jwtExpiration;
        protected boolean useUsernamePasswordAuthentication;
        protected Date dateAdded;
        protected String description;

        public Builder() {}

        public Builder(ClientDraft draft) {
            this.accountId = draft.accountId;
            this.name = draft.name;
            this.clientId = draft.clientId;
            this.clientSecret = draft.clientSecret;
            this.clientType = draft.clientType;
            this.jwtExpiration = draft.jwtExpiration;
            this.useUsernamePasswordAuthentication = draft.useUsernamePasswordAuthentication;
            this.dateAdded = draft.dateAdded;
            this.description = draft.description;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder accountId(long accountId) {
            this.accountId = accountId;
            return this;
        }


        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder clientType(ClientType clientType) {
            this.clientType = clientType;
            return this;
        }

        public Builder jwtExpiration(long jwtExpiration) {
            this.jwtExpiration = jwtExpiration;
            return this;
        }

        public Builder useUsernamePasswordAuthentication(boolean useUsernamePasswordAuthentication) {
            this.useUsernamePasswordAuthentication = useUsernamePasswordAuthentication;
            return this;
        }

        public Builder dateAdded(Date dateAdded) {
            this.dateAdded = dateAdded;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public ClientDraft build() {
            return new ClientDraft(accountId, name, clientId, clientSecret, clientType, jwtExpiration, useUsernamePasswordAuthentication,
                    dateAdded, description);
        }
    }
}
