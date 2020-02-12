package ua.tihiy.reminder.datasource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("test")
public class DataSourceConfigTest {

    @Bean
    public DataSource getDataSource() {

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:h2:mem:remindertestdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.username("sa");
        dataSourceBuilder.password("123");
        return dataSourceBuilder.build();

    }

}
