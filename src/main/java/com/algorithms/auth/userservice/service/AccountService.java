package com.algorithms.auth.userservice.service;

import com.algorithms.auth.userservice.domain.enums.Provider;
import com.algorithms.auth.userservice.result.Result;


public interface AccountService {

    Result completeRegistration(String code, String name);

    Result createCompany(String email, String username, String password, Provider provider, String providerId);

    Result nextDefaultCompanyName();

    Result getClient(String clientId);
}
