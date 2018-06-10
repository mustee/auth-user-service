package com.algorithms.auth.userservice.domain;

import com.algorithms.auth.userservice.domain.draft.CompanyDraft;

import java.util.Date;

public class Company extends CompanyDraft {
    private final long id;


    public Company(long id, String name, Date dateCreated){
        super(name, dateCreated);
        this.id = id;
    }

    public Company(long id, CompanyDraft draft){
        this(id, draft.getName(), draft.getDateCreated());
    }

    public long getId() {
        return id;
    }

    @Override
    public Builder draft() {
        return new Builder(this);
    }

}
