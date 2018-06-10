package com.algorithms.auth.userservice.service;

import com.algorithms.auth.userservice.domain.enums.Gender;
import com.algorithms.auth.userservice.domain.enums.Locale;
import com.algorithms.auth.userservice.domain.enums.Provider;
import com.algorithms.auth.userservice.result.Result;

public interface UserService {

    Result create(long companyId, String email, String username, String firstName, String middleName, String lastName,
                  Gender gender, Locale locale, String password, boolean isPrimaryAdmin, Provider provider, String providerId);
}
