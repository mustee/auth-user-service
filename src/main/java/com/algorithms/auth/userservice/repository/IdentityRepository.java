package com.algorithms.auth.userservice.repository;

import com.algorithms.auth.userservice.domain.Identity;
import com.algorithms.auth.userservice.domain.draft.IdentityDraft;

public interface IdentityRepository extends Repository<Identity> {

    long insert(IdentityDraft draft);

}
