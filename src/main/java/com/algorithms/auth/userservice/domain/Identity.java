package com.algorithms.auth.userservice.domain;

import com.algorithms.auth.userservice.domain.draft.IdentityDraft;
import com.algorithms.auth.userservice.domain.enums.Provider;

public class Identity extends IdentityDraft {
    private final long id;

    public Identity(long id, long userId, Provider provider, String providerId) {
        super(userId, provider, providerId);
        this.id = id;
    }

    public Identity(long id, IdentityDraft draft){
        this(id, draft.getUserId(), draft.getProvider(), draft.getProviderId());
    }

    public long getId() {
        return id;
    }

    @Override
    public Builder draft() {
        return new Builder(this);
    }
}
