package com.algorithms.auth.userservice.repository.impl;

import com.algorithms.auth.userservice.repository.UserRepository;
import com.algorithms.auth.userservice.repository.mapper.UserRowMapper;
import com.algorithms.auth.userservice.config.db.UserDatabaseQuery;
import com.algorithms.auth.userservice.domain.User;
import com.algorithms.auth.userservice.domain.draft.UserDraft;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Component
public class UserRepositoryImpl extends RepositoryBase<User> implements UserRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UserDatabaseQuery userDatabaseQuery;
    private final UserRowMapper userRowMapper;


    public UserRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, UserDatabaseQuery userDatabaseQuery) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.userDatabaseQuery = userDatabaseQuery;
        this.userRowMapper = new UserRowMapper();
    }

    @Override
    public Optional<User> get(long id) {
        List<User> users = namedParameterJdbcTemplate.query(userDatabaseQuery.FIND, new MapSqlParameterSource("id", id), userRowMapper);
        return singleResult(users);
    }

    @Override
    public int update(User user){
        Map<String, Object> map = new HashMap<>();
        map.put("first_name", user.getFirstName());
        map.put("middle_name", user.getMiddleName());
        map.put("last_name", user.getLastName());
        map.put("email", user.getEmail());
        map.put("username", user.getUsername());
        map.put("gender", user.getGender().name());
        map.put("locale", user.getLocale().name());
        map.put("password_hash", user.getPasswordHash());
        map.put("password_reset_token", user.getPasswordResetToken());
        map.put("security_stamp", user.getSecurityStamp());
        map.put("email_confirmation_token", user.getEmailConfirmationToken());
        map.put("email_confirmed", user.isEmailConfirmed());
        map.put("phone_number", user.getPhoneNumber());
        map.put("phone_number_confirmed", user.isPhoneNumberConfirmed());
        map.put("two_factor_enabled", user.isTwoFactorEnabled());
        map.put("lockout_end", user.getLockoutEnd());
        map.put("lockout_enabled", user.isLockoutEnabled());
        map.put("access_failed_count", user.getAccessFailedCount());
        map.put("is_primary_admin", user.isPrimaryAdmin());
        map.put("last_ip", user.getLastIp());
        map.put("date_created", user.getDateCreated());
        map.put("last_updated", user.getLastUpdated());
        map.put("last_login_date", user.getLastLoginDate());
        map.put("id", user.getId());

        return namedParameterJdbcTemplate.update(userDatabaseQuery.UPDATE, new MapSqlParameterSource(map));
    }

    @Override
    public long insert(UserDraft user){
        Map<String, Object> map = new HashMap<>();
        map.put("first_name", user.getFirstName());
        map.put("middle_name", user.getMiddleName());
        map.put("last_name", user.getLastName());
        map.put("email", user.getEmail());
        map.put("username", user.getUsername());
        map.put("gender", user.getGender() == null ? null : user.getGender().name());
        map.put("locale", user.getLocale() == null ? null : user.getLocale().name());
        map.put("password_hash", user.getPasswordHash());
        map.put("password_reset_token", user.getPasswordResetToken());
        map.put("security_stamp", user.getSecurityStamp());
        map.put("email_confirmation_token", user.getEmailConfirmationToken());
        map.put("email_confirmed", user.isEmailConfirmed());
        map.put("phone_number", user.getPhoneNumber());
        map.put("phone_number_confirmed", user.isPhoneNumberConfirmed());
        map.put("two_factor_enabled", user.isTwoFactorEnabled());
        map.put("lockout_end", user.getLockoutEnd());
        map.put("lockout_enabled", user.isLockoutEnabled());
        map.put("access_failed_count", user.getAccessFailedCount());
        map.put("is_primary_admin", user.isPrimaryAdmin());
        map.put("last_ip", user.getLastIp());
        map.put("date_created", user.getDateCreated());
        map.put("last_updated", user.getLastUpdated());
        map.put("last_login_date", user.getLastLoginDate());
        map.put("company_id", user.getCompanyId());

        KeyHolder holder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(userDatabaseQuery.INSERT, new MapSqlParameterSource(map), holder);
        return holder.getKey().longValue();
    }

    @Override
    public int delete(long id) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(namedParameterJdbcTemplate.getJdbcTemplate())
                .withProcedureName("sp_delete_user");
        Map<String, Object> map = new HashMap<>();
        map.put("userId", id);

        Map<String, Object> result = simpleJdbcCall.execute(new MapSqlParameterSource(map));
        return Integer.parseInt(result.entrySet().iterator().next().getValue().toString());
    }

    @Override
    public Optional<User> findByEmail(long companyId, String email)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("company_id", companyId);
        map.put("email", email);

        List<User> users = namedParameterJdbcTemplate.query(userDatabaseQuery.FIND_BY_EMAIL, new MapSqlParameterSource(map), userRowMapper);
        return singleResult(users);
    }

    @Override
    public Optional<User> findByEmailConfirmationToken(String token)
    {
        List<User> users = namedParameterJdbcTemplate.query(userDatabaseQuery.FIND_BY_EMAIL_CONFIRMATION_TOKEN,
                new MapSqlParameterSource("email_confirmation_token", token), userRowMapper);
        return singleResult(users);
    }

    @Override
    public Optional<User> findByPasswordResetToken(String token)
    {
        List<User> users = namedParameterJdbcTemplate.query(userDatabaseQuery.FIND_BY_PASSWORD_RESET_TOKEN,
                new MapSqlParameterSource("password_reset_token", token), userRowMapper);
        return singleResult(users);
    }

    @Override
    public Optional<User> findByUsername(long companyId, String username)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("company_id", companyId);
        map.put("username", username);

        List<User> users = namedParameterJdbcTemplate.query(userDatabaseQuery.FIND_BY_USERNAME, new MapSqlParameterSource(map), userRowMapper);
        return singleResult(users);
    }

    @Override
    public Optional<User> findPrimaryAdmin(long companyId)
    {
        List<User> users = namedParameterJdbcTemplate.query(userDatabaseQuery.FIND_PRIMARY_ADMIN,
                new MapSqlParameterSource("company_id", companyId), userRowMapper);
        return singleResult(users);
    }

    @Override
    public Optional<User> findPrimaryAdminByEmail(String email)
    {
        List<User> users = namedParameterJdbcTemplate.query(userDatabaseQuery.FIND_PRIMARY_ADMIN_BY_EMAIL,
                new MapSqlParameterSource("email", email), userRowMapper);
        return singleResult(users);
    }

}
