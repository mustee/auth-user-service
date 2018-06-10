package com.algorithms.auth.userservice.service.impl;

import com.algorithms.auth.userservice.repository.CompanyRepository;
import com.algorithms.auth.userservice.repository.IdentityRepository;
import com.algorithms.auth.userservice.repository.UserRepository;
import com.algorithms.auth.userservice.result.EntityResult;
import com.algorithms.auth.userservice.service.UserService;
import com.algorithms.auth.userservice.utils.PasswordHasher;
import com.algorithms.auth.userservice.domain.Company;
import com.algorithms.auth.userservice.domain.User;
import com.algorithms.auth.userservice.domain.draft.IdentityDraft;
import com.algorithms.auth.userservice.domain.draft.UserDraft;
import com.algorithms.auth.userservice.domain.enums.Gender;
import com.algorithms.auth.userservice.domain.enums.Locale;
import com.algorithms.auth.userservice.domain.enums.Provider;
import com.algorithms.auth.userservice.result.Result;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final IdentityRepository identityRepository;
    private final MessageSource messageSource;
    private final PasswordHasher passwordHasher;

    @Autowired
    public UserServiceImpl(CompanyRepository companyRepository, UserRepository userRepository, IdentityRepository identityRepository,
                           MessageSource messageSource, PasswordHasher passwordHasher){
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.identityRepository = identityRepository;
        this.messageSource = messageSource;
        this.passwordHasher = passwordHasher;
    }

    public Result create(long companyId, String email, String username, String firstName, String middleName, String lastName,
                         Gender gender, Locale locale, String password, boolean isPrimaryAdmin, Provider provider, String providerId)
    {
        Preconditions.checkArgument(StringUtils.isNotBlank(email), "Email cannot be null" );
        Preconditions.checkArgument(StringUtils.isNotBlank(username), "Username cannot be null" );
        Preconditions.checkNotNull(provider, "Provider cannot be null" );

        if (provider == Provider.Auth && StringUtils.isBlank(password)) throw new IllegalArgumentException("password");

        if (provider != Provider.Auth && StringUtils.isBlank(providerId)) throw new IllegalArgumentException("providerId");

        Optional<Company> company = companyRepository.get(companyId);
        if(!company.isPresent()){
            return Result.failed(messageSource.getMessage("invalid_company", null, java.util.Locale.getDefault()));
        }

        Optional<User> user = userRepository.findByEmail(company.get().getId(), email);
        if(user.isPresent()){
            return Result.failed(messageSource.getMessage("user_already_exists", null, java.util.Locale.getDefault()));
        }

        user = userRepository.findByUsername(company.get().getId(), username);
        if(user.isPresent()){
            return Result.failed(messageSource.getMessage("user_already_exists", null, java.util.Locale.getDefault()));
        }

        long id = userRepository.insert(new UserDraft(companyId, firstName, middleName, lastName, email, username,
                gender, locale, StringUtils.isBlank(password) ? null: passwordHasher.hash(password), null,
                UUID.randomUUID().toString(), null, false, null, false,
                false, null, false, 0, true, null,
                new Date(), new Date(), new Date()));

        if(provider == Provider.Auth) providerId = Long.toString(id);

        identityRepository.insert(new IdentityDraft(id, provider, providerId));
        return new EntityResult<>(id);
    }
}
