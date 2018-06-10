package com.algorithms.auth.userservice.config.db;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:database.properties")
public class AccountDatabaseQuery {

    @Value("${account.insert}")
    public String INSERT;

    @Value("${account.update}")
    public String UPDATE;

    @Value("${account.find}")
    public String FIND;

    @Value("${account.delete}")
    public String DELETE;

    @Value("${account.find_by_name}")
    public String FIND_BY_NAME;
}
