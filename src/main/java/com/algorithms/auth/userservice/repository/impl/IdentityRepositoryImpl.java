package com.algorithms.auth.userservice.repository.impl;

import com.algorithms.auth.userservice.repository.IdentityRepository;
import com.algorithms.auth.userservice.repository.mapper.IdentityRowMapper;
import com.algorithms.auth.userservice.config.db.IdentityDatabaseQuery;
import com.algorithms.auth.userservice.domain.Identity;
import com.algorithms.auth.userservice.domain.draft.IdentityDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;



@Component
public class IdentityRepositoryImpl extends RepositoryBase<Identity> implements IdentityRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final IdentityDatabaseQuery identityDatabaseQuery;
    private final IdentityRowMapper identityRowMapper;


    @Autowired
    public IdentityRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, IdentityDatabaseQuery identityDatabaseQuery) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.identityDatabaseQuery = identityDatabaseQuery;
        this.identityRowMapper = new IdentityRowMapper();

    }

    @Override
    public Optional<Identity> get(long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        List<Identity> identities = namedParameterJdbcTemplate.query(identityDatabaseQuery.FIND, namedParameters, identityRowMapper);
        return singleResult(identities);
    }

    @Override
    public int update(Identity identity) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", identity.getUserId());
        map.put("provider", identity.getProvider().name());
        map.put("provider_id", identity.getProviderId());
        map.put("id", identity.getId());

        return namedParameterJdbcTemplate.update(identityDatabaseQuery.UPDATE, new MapSqlParameterSource(map));
    }

    @Override
    public long insert(IdentityDraft identityDraft) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", identityDraft.getUserId());
        map.put("provider", identityDraft.getProvider().name());
        map.put("provider_id", identityDraft.getProviderId());

        KeyHolder holder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(identityDatabaseQuery.INSERT, new MapSqlParameterSource(map), holder);
        return holder.getKey().longValue();
    }

    @Override
    public int delete(long id) {
        return namedParameterJdbcTemplate.update(identityDatabaseQuery.DELETE, new MapSqlParameterSource("id", id));
    }
}
