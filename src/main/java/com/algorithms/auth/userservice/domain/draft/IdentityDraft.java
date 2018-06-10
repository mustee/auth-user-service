package com.algorithms.auth.userservice.domain.draft;

import com.algorithms.auth.userservice.domain.enums.Provider;

public class IdentityDraft {
    protected final long userId;
    protected final Provider provider;
    protected final String providerId;

    public IdentityDraft(long userId, Provider provider, String providerId) {
        this.userId = userId;
        this.provider = provider;
        this.providerId = providerId;
    }

    public long getUserId() {
        return userId;
    }

    public Provider getProvider() {
        return provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public Builder draft() {
        return new Builder(this);
    }




    public static class Builder {
        protected long userId;
        protected Provider provider;
        protected String providerId;

        public Builder() {
        }

        public Builder(IdentityDraft draft) {
            this.userId = draft.userId;
            this.provider = draft.provider;
            this.providerId = draft.providerId;
        }

        public Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder provider(Provider provider) {
            this.provider = provider;
            return this;
        }

        public Builder providerId(String providerId) {
            this.providerId = providerId;
            return this;
        }

        public IdentityDraft build() {
            return new IdentityDraft(userId, provider, providerId);
        }
    }
}
