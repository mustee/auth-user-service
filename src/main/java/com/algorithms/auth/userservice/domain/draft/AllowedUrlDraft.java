package com.algorithms.auth.userservice.domain.draft;

import com.algorithms.auth.userservice.domain.enums.UrlType;


public class AllowedUrlDraft {
    protected final long clientId;
    protected final String url;
    protected final UrlType urlType;

    public AllowedUrlDraft(long clientId, String url, UrlType urlType) {
        this.clientId = clientId;
        this.url = url;
        this.urlType = urlType;
    }


    public long getClientId() {
        return clientId;
    }

    public String getUrl() {
        return url;
    }

    public UrlType getUrlType() {
        return urlType;
    }

    public Builder draft() {
        return new Builder(this);
    }



    public static class Builder {
        protected long clientId;
        protected String url;
        protected UrlType urlType;

        public Builder() {}

        public Builder(AllowedUrlDraft draft) {
            this.clientId = draft.clientId;
            this.url = draft.url;
            this.urlType = draft.urlType;
        }

        public Builder clientId(long clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder urlType(UrlType urlType) {
            this.urlType = urlType;
            return this;
        }

        public AllowedUrlDraft build() {
            return new AllowedUrlDraft(clientId, url, urlType);
        }
    }
}
