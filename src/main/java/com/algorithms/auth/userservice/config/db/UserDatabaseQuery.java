package com.algorithms.auth.userservice.config.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:database.properties")
public class UserDatabaseQuery {

    @Value("${user.insert}")
    public String INSERT;

    @Value("${user.update}")
    public String UPDATE;

    @Value("${user.find}")
    public String FIND;

    @Value("${user.delete}")
    public String DELETE;

    @Value("${user.find_by_email}")
    public String FIND_BY_EMAIL;

    @Value("${user.find_by_email_confirmation_token}")
    public String FIND_BY_EMAIL_CONFIRMATION_TOKEN;

    @Value("${user.find_by_password_reset_token}")
    public String FIND_BY_PASSWORD_RESET_TOKEN;

    @Value("${user.find_by_username}")
    public String FIND_BY_USERNAME;

    @Value("${user.find_primary_admin}")
    public String FIND_PRIMARY_ADMIN;

    @Value("${user.find_primary_admin_by_email}")
    public String FIND_PRIMARY_ADMIN_BY_EMAIL;
}
