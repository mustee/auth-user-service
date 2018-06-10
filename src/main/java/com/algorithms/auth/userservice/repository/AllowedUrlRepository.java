package com.algorithms.auth.userservice.repository;

import com.algorithms.auth.userservice.domain.AllowedUrl;
import com.algorithms.auth.userservice.domain.draft.AllowedUrlDraft;

import java.util.List;


public interface AllowedUrlRepository extends Repository<AllowedUrl> {

    long insert(AllowedUrlDraft draft);

    List<AllowedUrl> findByClientId(long clientId);
}
