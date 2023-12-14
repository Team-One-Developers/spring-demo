package de.t1dev.springdemo.config.db.sqlite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class SqliteDbConfiguration {
    private final Environment env;

    @Autowired
    public SqliteDbConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();

        final var driverClassName = env.getProperty("driverClassName");

        if (driverClassName == null) {
            throw new IllegalStateException();
        }

        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(env.getProperty("url"));
        dataSource.setUsername(env.getProperty("user"));
        dataSource.setPassword(env.getProperty("password"));
        return dataSource;
    }
}
