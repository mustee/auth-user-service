package com.algorithms.auth.userservice.repository;

import com.algorithms.auth.userservice.domain.Account;
import com.algorithms.auth.userservice.domain.draft.AccountDraft;

import java.util.Optional;


public interface AccountRepository extends Repository<Account> {

    long insert(AccountDraft draft);

    Optional<Account> findByName(long companyId, String name);
}
