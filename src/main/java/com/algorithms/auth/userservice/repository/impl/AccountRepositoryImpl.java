package com.algorithms.auth.userservice.repository.impl;

import com.algorithms.auth.userservice.repository.AccountRepository;
import com.algorithms.auth.userservice.repository.mapper.AccountRowMapper;
import com.algorithms.auth.userservice.config.db.AccountDatabaseQuery;
import com.algorithms.auth.userservice.domain.Account;
import com.algorithms.auth.userservice.domain.draft.AccountDraft;
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
public class AccountRepositoryImpl extends RepositoryBase<Account> implements AccountRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final AccountDatabaseQuery accountDatabaseQuery;
    private final AccountRowMapper accountRowMapper;


    @Autowired
    public AccountRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, AccountDatabaseQuery accountDatabaseQuery) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.accountDatabaseQuery = accountDatabaseQuery;
        this.accountRowMapper = new AccountRowMapper();
    }

    @Override
    public Optional<Account> get(long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        List<Account> accounts = namedParameterJdbcTemplate.query(accountDatabaseQuery.FIND, namedParameters, accountRowMapper);
        return singleResult(accounts);
    }

    @Override
    public int update(Account account) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", account.getId());
        map.put("name", account.getName());
        map.put("date_added", account.getDateAdded());
        map.put("required_length", account.getRequiredLength());
        map.put("require_non_letter_or_digit", account.isRequireNonLetterOrDigit());
        map.put("require_digit", account.isRequireDigit());
        map.put("require_lowercase", account.isRequireLowercase());
        map.put("require_uppercase", account.isRequireUppercase());

        return namedParameterJdbcTemplate.update(accountDatabaseQuery.UPDATE, new MapSqlParameterSource(map));
    }

    @Override
    public long insert(AccountDraft account) {
        Map<String, Object> map = new HashMap<>();
        map.put("company_id", account.getCompanyId());
        map.put("name", account.getName());
        map.put("date_added", account.getDateAdded());
        map.put("required_length", account.getRequiredLength());
        map.put("require_non_letter_or_digit", account.isRequireNonLetterOrDigit());
        map.put("require_digit", account.isRequireDigit());
        map.put("require_lowercase", account.isRequireLowercase());
        map.put("require_uppercase", account.isRequireUppercase());

        KeyHolder holder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(accountDatabaseQuery.INSERT, new MapSqlParameterSource(map), holder);
        return holder.getKey().longValue();
    }

    @Override
    public int delete(long id) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(namedParameterJdbcTemplate.getJdbcTemplate())
                .withProcedureName("sp_delete_account");
        Map<String, Object> map = new HashMap<>();
        map.put("accountId", id);

        Map<String, Object> result = simpleJdbcCall.execute(new MapSqlParameterSource(map));
        return Integer.parseInt(result.entrySet().iterator().next().getValue().toString());
    }

    public Optional<Account> findByName(long companyId, String name){
        Map<String, String> map = new HashMap<>();
        map.put("company_id", Long.toString(companyId));
        map.put("name", name);

        List<Account> accounts = namedParameterJdbcTemplate.query(accountDatabaseQuery.FIND_BY_NAME, new MapSqlParameterSource(map), accountRowMapper);
        return singleResult(accounts);
    }
}
