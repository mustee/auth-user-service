package com.algorithms.auth.userservice.repository.impl;

import com.algorithms.auth.userservice.repository.ClientRepository;
import com.algorithms.auth.userservice.repository.mapper.ClientRowMapper;
import com.algorithms.auth.userservice.config.db.ClientDatabaseQuery;
import com.algorithms.auth.userservice.domain.Client;
import com.algorithms.auth.userservice.domain.draft.ClientDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Component
public class ClientRepositoryImpl extends RepositoryBase<Client> implements ClientRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final ClientDatabaseQuery clientDatabaseQuery;
    private final ClientRowMapper clientRowMapper;

    @Autowired
    public ClientRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, ClientDatabaseQuery clientDatabaseQuery) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.clientDatabaseQuery = clientDatabaseQuery;
        this.clientRowMapper = new ClientRowMapper();
    }

    @Override
    public Optional<Client> get(long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        List<Client> clients = namedParameterJdbcTemplate.query(clientDatabaseQuery.FIND, namedParameters, clientRowMapper);
        return singleResult(clients);
    }

    @Override
    public int update(Client client) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", client.getId());
        map.put("name", client.getName());
        map.put("client_id", client.getClientId());
        map.put("client_secret", client.getClientSecret());
        map.put("client_type", client.getClientType().name());
        map.put("jwt_expiration", client.getJwtExpiration());
        map.put("use_username_password_authentication", client.isUseUsernamePasswordAuthentication());
        map.put("date_added", client.getDateAdded());
        map.put("description", client.getDescription());

        return namedParameterJdbcTemplate.update(clientDatabaseQuery.UPDATE, new MapSqlParameterSource(map));
    }

    @Override
    public long insert(ClientDraft client) {
        Map<String, Object> map = new HashMap<>();
        map.put("account_id", client.getAccountId());
        map.put("name", client.getName());
        map.put("client_id", client.getClientId());
        map.put("client_secret", client.getClientSecret());
        map.put("client_type", client.getClientType().name());
        map.put("jwt_expiration", client.getJwtExpiration());
        map.put("use_username_password_authentication", client.isUseUsernamePasswordAuthentication());
        map.put("date_added", client.getDateAdded());
        map.put("description", client.getDescription());

        KeyHolder holder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(clientDatabaseQuery.INSERT, new MapSqlParameterSource(map), holder);
        return holder.getKey().longValue();
    }

    @Override
    public Optional<Client> findByClientId(String clientId) {
        List<Client> clients = namedParameterJdbcTemplate.query(clientDatabaseQuery.FIND_BY_CLIENT_ID,
                new MapSqlParameterSource("client_id", clientId), clientRowMapper);
        return singleResult(clients);
    }

    @Override
    public int delete(long id) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(namedParameterJdbcTemplate.getJdbcTemplate())
                .withProcedureName("sp_delete_client");
        Map<String, Object> map = new HashMap<>();
        map.put("clientId", id);

        Map<String, Object> result = simpleJdbcCall.execute(new MapSqlParameterSource(map));
        return Integer.parseInt(result.entrySet().iterator().next().getValue().toString());
    }
}
