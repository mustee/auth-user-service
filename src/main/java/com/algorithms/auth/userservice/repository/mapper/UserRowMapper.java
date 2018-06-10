package com.algorithms.auth.userservice.repository.mapper;

import com.algorithms.auth.userservice.domain.User;
import com.algorithms.auth.userservice.domain.enums.Gender;
import com.algorithms.auth.userservice.domain.enums.Locale;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getLong("id"), rs.getLong("company_id"), rs.getNString("first_name"),
                rs.getNString("middle_name"), rs.getNString("last_name"), rs.getNString("email"),
                rs.getNString("username"), StringUtils.isNotBlank(rs.getNString("gender")) ? Gender.valueOf(rs.getNString("gender")) : null,
                StringUtils.isNotBlank(rs.getNString("locale")) ? Locale.valueOf(rs.getNString("locale")) : null,
                rs.getNString("password_hash"), rs.getNString("password_reset_token"),
                rs.getNString("security_stamp"), rs.getNString("email_confirmation_token"), rs.getBoolean("email_confirmed"),
                rs.getNString("phone_number"), rs.getBoolean("phone_number_confirmed"), rs.getBoolean("two_factor_enabled"),
                rs.getDate("lockout_end"), rs.getBoolean("lockout_enabled"), rs.getInt("access_failed_count"),
                rs.getBoolean("is_primary_admin"), rs.getNString("last_ip"), rs.getTimestamp("date_created"),
                rs.getTimestamp("last_updated"), rs.getTimestamp("last_login_date"));

    }

}