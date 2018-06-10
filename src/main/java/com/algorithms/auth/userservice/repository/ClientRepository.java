package com.algorithms.auth.userservice.repository;

import com.algorithms.auth.userservice.domain.Client;
import com.algorithms.auth.userservice.domain.draft.ClientDraft;

import java.util.Optional;


public interface ClientRepository extends Repository<Client> {

    long insert(ClientDraft draft);

    Optional<Client> findByClientId(String clientId);
}
