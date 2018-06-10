package com.algorithms.auth.userservice.service.impl;

import com.algorithms.auth.userservice.repository.ClientRepository;
import com.algorithms.auth.userservice.repository.CompanyRepository;
import com.algorithms.auth.userservice.repository.UserRepository;
import com.algorithms.auth.userservice.service.UserService;
import com.algorithms.auth.userservice.utils.Defaults;
import com.algorithms.auth.userservice.domain.Client;
import com.algorithms.auth.userservice.domain.Company;
import com.algorithms.auth.userservice.domain.User;
import com.algorithms.auth.userservice.domain.draft.CompanyDraft;
import com.algorithms.auth.userservice.domain.draft.UserDraft;
import com.algorithms.auth.userservice.domain.enums.Locale;
import com.algorithms.auth.userservice.domain.enums.Provider;
import com.algorithms.auth.userservice.result.EntityResult;
import com.algorithms.auth.userservice.result.Result;
import com.algorithms.auth.userservice.result.models.ClientModel;
import com.algorithms.auth.userservice.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AccountServiceImpl implements AccountService {
    private final UserService userService;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final MessageSource messageSource;

    @Autowired
    public AccountServiceImpl(UserService userService, CompanyRepository companyRepository, UserRepository userRepository,
                              ClientRepository clientRepository, MessageSource messageSource)
    {
        this.userService = userService;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.messageSource = messageSource;
    }

    public Result completeRegistration(String code, String name)
    {
        Preconditions.checkArgument(StringUtils.isNotBlank(code), "Code cannot be null" );
        Preconditions.checkArgument(StringUtils.isNotBlank(name), "Name cannot be null" );

        final Optional<User> user = userRepository.findByEmailConfirmationToken(code);
        if (!user.isPresent())
        {
            return Result.failed(messageSource.getMessage("confirmation_failed", null, java.util.Locale.getDefault()));
        }

        final Optional<Company> company = companyRepository.get(user.get().getId());
        if (!company.isPresent())
        {
            return Result.failed(messageSource.getMessage("company_not_found", null, java.util.Locale.getDefault()));
        }

        companyRepository.update(new Company(company.get().getId(), company.get().draft().name(name).build()));

        final UserDraft updateUser = user.get().draft().emailConfirmed(true).build();
        userRepository.update(new User(user.get().getId(), updateUser));
        return new EntityResult<>(updateUser);
    }

    public Result createCompany(String email, String username, String password, Provider provider, String providerId)
    {
        Preconditions.checkArgument(StringUtils.isNotBlank(email), "Email cannot be null" );
        Preconditions.checkArgument(StringUtils.isNotBlank(username), "Username cannot be null" );
        Preconditions.checkNotNull(provider, "Provider cannot be null" );

        if (provider == Provider.Auth && StringUtils.isBlank(password)) throw new IllegalArgumentException("password");

        if (provider != Provider.Auth && StringUtils.isBlank(providerId)) throw new IllegalArgumentException("providerId");

        Optional<User> user = userRepository.findPrimaryAdminByEmail(email);
        if (user.isPresent())
        {
            return Result.failed(messageSource.getMessage("duplicate_account_holder", new Object[]{email}, java.util.Locale.getDefault()));
        }

        CompanyDraft draft = new CompanyDraft(((EntityResult<String>)nextDefaultCompanyName()).getEntity(),
                Date.from (LocalDateTime.now(Clock.systemUTC()).toInstant(ZoneOffset.UTC)));
        long id = companyRepository.insert(draft);

        userService.create(id, email, username, null, null, null, null, Locale.English,
                password, true,provider, providerId);

        return Result.succeeded();
    }


    public Result nextDefaultCompanyName(){
        List<String> names = companyRepository.companyNamesLikeDefault();

        return new EntityResult<>(Defaults.COMPANY_NAME + " " + (getRecentDefaultNumber(names) + 1));

    }

    @Override
    public Result getClient(String clientId) {
        Optional<Client> client = clientRepository.findByClientId(clientId);
        if(!client.isPresent()){
            return Result.failed(messageSource.getMessage("client_not_found", null, java.util.Locale.getDefault()));
        }

        return new EntityResult<>(ClientModel.from(client.get()));
    }


    private int getRecentDefaultNumber(List<String> names){
        List<String> sortedNames = names.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        for(String name: sortedNames){
            String number = name.replace(Defaults.COMPANY_NAME, "").trim();
            if(StringUtils.isNumeric(number)){
                return Integer.parseInt(number);
            }
        }

        return 0;
    }
}
