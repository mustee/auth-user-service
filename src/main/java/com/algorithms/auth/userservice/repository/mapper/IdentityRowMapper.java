package com.algorithms.auth.userservice.repository.mapper;

import com.algorithms.auth.userservice.domain.Identity;
import com.algorithms.auth.userservice.domain.enums.Provider;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IdentityRowMapper implements RowMapper<Identity> {

    @Override
    public Identity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Identity(rs.getLong("id"), rs.getLong("user_id"), Provider.valueOf(rs.getNString("provider")),
                rs.getNString("provider_id"));
    }
}
