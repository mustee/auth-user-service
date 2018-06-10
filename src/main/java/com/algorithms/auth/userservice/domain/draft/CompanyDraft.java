package com.algorithms.auth.userservice.domain.draft;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class CompanyDraft {
    protected final String name;
    protected final Date dateCreated;

    public CompanyDraft(String name, Date dateCreated){
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Builder draft() {
        return new Builder(this);
    }

    public static class Builder {
        protected String name;
        protected Date dateCreated;

        public Builder() {}

        public Builder(@NotNull CompanyDraft draft) {
            this.name = draft.name;
            this.dateCreated = draft.dateCreated;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder dateCreated(Date dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        public CompanyDraft build() {
            return new CompanyDraft(name, dateCreated);
        }
    }
}
