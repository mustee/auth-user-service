package com.algorithms.auth.userservice.repository.mapper;

import com.algorithms.auth.userservice.domain.AllowedUrl;
import com.algorithms.auth.userservice.domain.enums.UrlType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AllowedUrlRowMapper implements RowMapper<AllowedUrl> {

    @Override
    public AllowedUrl mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AllowedUrl(rs.getLong("id"), rs.getLong("client_id"), rs.getNString("url"),
                UrlType.valueOf(rs.getNString("url_type")));
    }

}
