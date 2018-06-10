package com.algorithms.auth.userservice.repository.mapper;

import com.algorithms.auth.userservice.domain.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Account(rs.getLong("id"), rs.getLong("company_id"), rs.getNString("name"),
                rs.getTimestamp("date_added"), rs.getInt("required_length"), rs.getBoolean("require_non_letter_or_digit"),
                rs.getBoolean("require_digit"), rs.getBoolean("require_lowercase"), rs.getBoolean("require_uppercase"));
    }

}