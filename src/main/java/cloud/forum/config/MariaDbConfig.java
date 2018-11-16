package cloud.forum.config;

import ch.vorburger.mariadb4j.MariaDB4jService;
import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
//Configuration for the database we use locally - mariaDB - on the deployed version there is a HSQLDB instead. 
// This runs when a specific profile is selected, as specified in application.yml
@Configuration
@Profile("embedded-mariadb")
public class MariaDbConfig {
    @Bean
    @Primary
    public DataSource mariaDbDataSource(DataSourceProperties dataSourceProperties, MariaDB4jService mariaDB4jService) throws Exception {
        mariaDB4jService.getDB().createDB("forum_db");
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public MariaDB4jService mariaDB4jService() {
        return new MariaDB4jSpringService();
    }
}
