package com.algorithms.auth.userservice.repository;

import com.algorithms.auth.userservice.domain.User;
import com.algorithms.auth.userservice.domain.draft.UserDraft;

import java.util.Optional;


public interface UserRepository extends Repository<User> {

    long insert(UserDraft draft);

    Optional<User> findByEmail(long companyId, String email);

    Optional<User> findByEmailConfirmationToken(String token);

    Optional<User> findByPasswordResetToken(String token);

    Optional<User> findByUsername(long companyId, String username);

    Optional<User> findPrimaryAdmin(long companyId);

    Optional<User> findPrimaryAdminByEmail(String email);

}
