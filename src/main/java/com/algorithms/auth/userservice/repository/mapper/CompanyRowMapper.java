package com.algorithms.auth.userservice.repository.mapper;

import com.algorithms.auth.userservice.domain.Company;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class CompanyRowMapper implements RowMapper<Company> {

    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Company(rs.getLong("id"), rs.getNString("name"), rs.getTimestamp("date_created"));
    }

}
