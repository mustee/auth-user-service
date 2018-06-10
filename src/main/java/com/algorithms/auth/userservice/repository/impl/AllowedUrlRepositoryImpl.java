package com.algorithms.auth.userservice.repository.impl;

import com.algorithms.auth.userservice.repository.AllowedUrlRepository;
import com.algorithms.auth.userservice.repository.mapper.AllowedUrlRowMapper;
import com.algorithms.auth.userservice.config.db.AllowedUrlDatabaseQuery;
import com.algorithms.auth.userservice.domain.AllowedUrl;
import com.algorithms.auth.userservice.domain.draft.AllowedUrlDraft;
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
public class AllowedUrlRepositoryImpl extends RepositoryBase<AllowedUrl> implements AllowedUrlRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final AllowedUrlDatabaseQuery allowedUrlDatabaseQuery;
    private final AllowedUrlRowMapper allowedUrlRowMapper;


    @Autowired
    public AllowedUrlRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, AllowedUrlDatabaseQuery allowedUrlDatabaseQuery) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.allowedUrlDatabaseQuery = allowedUrlDatabaseQuery;
        this.allowedUrlRowMapper = new AllowedUrlRowMapper();
    }

    @Override
    public Optional<AllowedUrl> get(long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        List<AllowedUrl> allowedUrls = namedParameterJdbcTemplate.query(allowedUrlDatabaseQuery.FIND, namedParameters, allowedUrlRowMapper);
        return singleResult(allowedUrls);
    }

    @Override
    public int update(AllowedUrl allowedUrl) {
        Map<String, Object> map = new HashMap<>();
        map.put("client_id", allowedUrl.getClientId());
        map.put("url", allowedUrl.getUrl());
        map.put("url_type", allowedUrl.getUrlType().name());
        map.put("id", allowedUrl.getId());

        return namedParameterJdbcTemplate.update(allowedUrlDatabaseQuery.UPDATE, new MapSqlParameterSource(map));
    }

    @Override
    public long insert(AllowedUrlDraft allowedUrl) {
        Map<String, Object> map = new HashMap<>();
        map.put("client_id", allowedUrl.getClientId());
        map.put("url", allowedUrl.getUrl());
        map.put("url_type", allowedUrl.getUrlType().name());

        KeyHolder holder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(allowedUrlDatabaseQuery.INSERT, new MapSqlParameterSource(map), holder);
        return holder.getKey().longValue();
    }

    @Override
    public List<AllowedUrl> findByClientId(long clientId) {
        return namedParameterJdbcTemplate.query(allowedUrlDatabaseQuery.FIND_BY_CLIENT_ID,
                new MapSqlParameterSource("client_id", clientId), allowedUrlRowMapper);
    }

    @Override
    public int delete(long id) {
        return namedParameterJdbcTemplate.update(allowedUrlDatabaseQuery.DELETE, new MapSqlParameterSource("id", id));
    }
}
