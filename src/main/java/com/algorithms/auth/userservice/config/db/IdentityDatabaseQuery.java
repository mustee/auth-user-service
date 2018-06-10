package com.algorithms.auth.userservice.config.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:database.properties")
public class IdentityDatabaseQuery {

    @Value("${identity.find}")
    public String FIND;

    @Value("${identity.insert}")
    public String INSERT;

    @Value("${identity.update}")
    public String UPDATE;

    @Value("${identity.delete}")
    public String DELETE;
}
