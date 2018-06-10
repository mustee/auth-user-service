package com.algorithms.auth.userservice.config.db;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:database.properties")
public class CompanyDatabaseQuery {

    @Value("${company.find}")
    public String FIND;

    @Value("${company.insert}")
    public String INSERT;

    @Value("${company.update}")
    public String UPDATE;

    @Value("${company.delete}")
    public String DELETE;

    @Value("${company.company_names_like_default}")
    public String COMPANY_NAMES_LIKE_DEFAULT;

}
