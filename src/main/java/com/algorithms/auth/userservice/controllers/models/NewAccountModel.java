package com.algorithms.auth.userservice.controllers.models;

import javax.validation.constraints.NotNull;

public class NewAccountModel {

    @NotNull
    private long companyId;

    @NotNull
    private String name;

    public long getCompanyId() {
        return companyId;
    }

    public String getName() {
        return name;
    }
}
