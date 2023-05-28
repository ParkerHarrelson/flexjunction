package com.flexjunction.usermanagement.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DBConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "flex-junction.user-management.datasource")
    public DataSource dataSource(@Value("${flex-junction.user-management.datasource.user}") String username,
                                 @Value("${flex-junction.user-management.datasource.password}") String password) {
        return DataSourceBuilder.create().username(username).password(password).build();
    }
}
