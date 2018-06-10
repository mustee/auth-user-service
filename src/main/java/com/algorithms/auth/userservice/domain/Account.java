package com.algorithms.auth.userservice.domain;

import com.algorithms.auth.userservice.domain.draft.AccountDraft;


import java.util.Date;


public class Account extends AccountDraft {
    private final long id;


    public Account(long id, long companyId, String name, Date dateAdded, int requiredLength, boolean requireNonLetterOrDigit,
                        boolean requireDigit, boolean requireLowercase, boolean requireUppercase) {
        super(companyId, name, dateAdded, requiredLength, requireNonLetterOrDigit, requireDigit, requireLowercase, requireUppercase);
        this.id = id;
    }

    public Account(long id, AccountDraft draft) {
        this(id, draft.getCompanyId(), draft.getName(), draft.getDateAdded(), draft.getRequiredLength(), draft.isRequireNonLetterOrDigit(),
                draft.isRequireDigit(), draft.isRequireLowercase(), draft.isRequireUppercase());
    }

    public long getId() {
        return id;
    }

    @Override
    public Builder draft() {
        return new Builder(this);
    }
}
