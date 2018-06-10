package com.algorithms.auth.userservice.config.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:database.properties")
public class ClientDatabaseQuery {

    @Value("${client.find}")
    public String FIND;

    @Value("${client.insert}")
    public String INSERT;

    @Value("${client.update}")
    public String UPDATE;

    @Value("${client.find_by_client_id}")
    public String FIND_BY_CLIENT_ID;

}
