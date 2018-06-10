package com.algorithms.auth.userservice.repository.impl;

import com.algorithms.auth.userservice.repository.CompanyRepository;
import com.algorithms.auth.userservice.repository.mapper.CompanyRowMapper;
import com.algorithms.auth.userservice.utils.Defaults;
import com.algorithms.auth.userservice.config.db.CompanyDatabaseQuery;
import com.algorithms.auth.userservice.domain.Company;
import com.algorithms.auth.userservice.domain.draft.CompanyDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CompanyRepositoryImpl extends RepositoryBase<Company> implements CompanyRepository {
    private final CompanyRowMapper companyRowMapper;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final CompanyDatabaseQuery companyDatabaseQuery;

    @Autowired
    public CompanyRepositoryImpl( NamedParameterJdbcTemplate namedParameterJdbcTemplate, CompanyDatabaseQuery companyDatabaseQuery) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.companyDatabaseQuery = companyDatabaseQuery;
        this.companyRowMapper = new CompanyRowMapper();
    }


    @Override
    public Optional<Company> get(long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        List<Company> companies = namedParameterJdbcTemplate.query(companyDatabaseQuery.FIND, namedParameters, companyRowMapper);
        return singleResult(companies);
    }

    @Override
    public int update(Company company) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", company.getName());
        map.put("date_created", company.getDateCreated());
        map.put("id", company.getId());

        return namedParameterJdbcTemplate.update(companyDatabaseQuery.UPDATE, new MapSqlParameterSource(map));
    }

    @Override
    public long insert(CompanyDraft company) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("name", company.getName(), Types.NVARCHAR)
                .addValue("date_created", new java.sql.Timestamp(company.getDateCreated().getTime()), Types.TIMESTAMP);

        KeyHolder holder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(companyDatabaseQuery.INSERT, mapSqlParameterSource, holder);
        return holder.getKey().longValue();
    }

    @Override
    public List<String> companyNamesLikeDefault() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", Defaults.COMPANY_NAME + "%");

        return namedParameterJdbcTemplate.query(companyDatabaseQuery.COMPANY_NAMES_LIKE_DEFAULT,
                map, (rs, rowNum) -> rs.getNString(1));
    }

    @Override
    public int delete(long id) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(namedParameterJdbcTemplate.getJdbcTemplate())
                .withProcedureName("sp_delete_company");
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", id);

        Map<String, Object> result = simpleJdbcCall.execute(new MapSqlParameterSource(map));
        return Integer.parseInt(result.entrySet().iterator().next().getValue().toString());
    }
}
