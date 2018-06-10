package com.algorithms.auth.userservice.repository.mapper;

import com.algorithms.auth.userservice.domain.Client;
import com.algorithms.auth.userservice.domain.enums.ClientType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRowMapper implements RowMapper<Client> {

    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Client(rs.getLong("id"), rs.getLong("account_id"), rs.getNString("name"),
                rs.getNString("client_id"), rs.getNString("client_secret"), ClientType.valueOf(rs.getNString("client_type")),
                rs.getLong("jwt_expiration"), rs.getBoolean("use_username_password_authentication"),
                rs.getTimestamp("date_added"), rs.getNString("description"));
    }

}