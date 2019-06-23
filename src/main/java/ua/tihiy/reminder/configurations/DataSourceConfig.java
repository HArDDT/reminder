package ua.tihiy.reminder.configurations;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:properties/datasource.properties")
public class DataSourceConfig {

    @Bean
    public DataSource getDataSource(Environment environment) {

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(environment.getProperty("datasource.driver"));
        dataSourceBuilder.url(environment.getProperty("datasource.url"));
        dataSourceBuilder.username(environment.getProperty("datasource.username"));
        dataSourceBuilder.password(environment.getProperty("datasource.password"));
        return dataSourceBuilder.build();
    }

}
