package org.ejemplo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration //Estamos indicando que este obejto contiene beans de configuracion
public class JdbcConfiguration {

    @Bean
    public DataSource dataSource() { //Este método se ejecuta para crear un bean de configuracion que nos permite conectarnos a nuestra base de datos
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/p3");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("admin");
        driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return driverManagerDataSource;
    }
}
