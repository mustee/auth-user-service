package com.algorithms.auth.userservice.config.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:database.properties")
public class AllowedUrlDatabaseQuery {

    @Value("${allowed_url.find}")
    public String FIND;

    @Value("${allowed_url.insert}")
    public String INSERT;

    @Value("${allowed_url.update}")
    public String UPDATE;

    @Value("${allowed_url.delete}")
    public String DELETE;

    @Value("${allowed_url.find_by_client_id}")
    public String FIND_BY_CLIENT_ID;

}
