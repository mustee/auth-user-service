package com.algorithms.auth.userservice.domain;

import com.algorithms.auth.userservice.domain.draft.AllowedUrlDraft;
import com.algorithms.auth.userservice.domain.enums.UrlType;

public class AllowedUrl extends AllowedUrlDraft {
    private final long id;

    public AllowedUrl(long id, long clientId, String url, UrlType urlType) {
        super(clientId, url, urlType);
        this.id = id;
    }

    public AllowedUrl(long id, AllowedUrlDraft draft){
        this(id, draft.getClientId(), draft.getUrl(), draft.getUrlType());
    }

    public long getId() {
        return id;
    }

    @Override
    public Builder draft() {
        return new Builder(this);
    }
}
