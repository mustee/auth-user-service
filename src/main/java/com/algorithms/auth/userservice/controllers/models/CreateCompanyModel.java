package com.algorithms.auth.userservice.controllers.models;

import javax.validation.constraints.NotNull;



public class CreateCompanyModel {

    public CreateCompanyModel(){}

    public CreateCompanyModel(String email, String password, String provider, String providerId){
        this.email = email;
        this.password = password;
        this.provider = provider;
        this.providerId = providerId;
    }

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String provider;

    private String providerId;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getProvider() {
        return provider;
    }

    public String getProviderId() {
        return providerId;
    }
}
