package com.algorithms.auth.userservice.domain.draft;

import java.util.Date;

public class AccountDraft {
    protected final long companyId;
    protected final String name;
    protected final Date dateAdded;
    protected final int requiredLength;
    protected final boolean requireNonLetterOrDigit;
    protected final boolean requireDigit;
    protected final boolean requireLowercase;
    protected final boolean requireUppercase;

    public AccountDraft(long companyId, String name, Date dateAdded, int requiredLength, boolean requireNonLetterOrDigit,
        boolean requireDigit, boolean requireLowercase, boolean requireUppercase) {
        this.companyId = companyId;
        this.name = name;
        this.dateAdded = dateAdded;
        this.requiredLength = requiredLength;
        this.requireNonLetterOrDigit = requireNonLetterOrDigit;
        this.requireDigit = requireDigit;
        this.requireLowercase = requireLowercase;
        this.requireUppercase = requireUppercase;
    }

    public long getCompanyId() {
        return companyId;
    }

    public String getName() {
        return name;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public int getRequiredLength() {
        return requiredLength;
    }

    public boolean isRequireNonLetterOrDigit() {
        return requireNonLetterOrDigit;
    }

    public boolean isRequireDigit() {
        return requireDigit;
    }

    public boolean isRequireLowercase() {
        return requireLowercase;
    }

    public boolean isRequireUppercase() {
        return requireUppercase;
    }

    public Builder draft() {
        return new Builder(this);
    }

    public static class Builder {
        protected long companyId;
        protected String name;
        protected Date dateAdded;
        protected int requiredLength;
        protected boolean requireNonLetterOrDigit;
        protected boolean requireDigit;
        protected boolean requireLowercase;
        protected boolean requireUppercase;

        Builder() {}

        public Builder(AccountDraft draft) {
            this.companyId = draft.companyId;
            this.name = draft.name;
            this.dateAdded = draft.dateAdded;
            this.requiredLength = draft.requiredLength;
            this.requireNonLetterOrDigit = draft.requireNonLetterOrDigit;
            this.requireDigit = draft.requireDigit;
            this.requireLowercase = draft.requireLowercase;
            this.requireUppercase = draft.requireUppercase;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder companyId(long companyId) {
            this.companyId = companyId;
            return this;
        }

        public Builder dateAdded(Date dateAdded) {
            this.dateAdded = dateAdded;
            return this;
        }

        public Builder requiredLength(int requiredLength) {
            this.requiredLength = requiredLength;
            return this;
        }


        public Builder requireNonLetterOrDigit(boolean requireNonLetterOrDigit) {
            this.requireNonLetterOrDigit = requireNonLetterOrDigit;
            return this;
        }

        public Builder requireDigit(boolean requireDigit) {
            this.requireDigit = requireDigit;
            return this;
        }

        public Builder requireUppercase(boolean requireUppercase) {
            this.requireUppercase = requireUppercase;
            return this;
        }

        public Builder requireLowercase(boolean requireLowercase) {
            this.requireLowercase = requireLowercase;
            return this;
        }

        public AccountDraft build() {
            return new AccountDraft(companyId, name, dateAdded, requiredLength, requireNonLetterOrDigit, requireDigit,
                    requireLowercase, requireUppercase);
        }
    }
}
