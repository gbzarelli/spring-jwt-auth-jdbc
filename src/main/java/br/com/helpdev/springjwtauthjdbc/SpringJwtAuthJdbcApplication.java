package br.com.helpdev.springjwtauthjdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringJwtAuthJdbcApplication {

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/sample_jwt_db");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("123456");
        return driverManagerDataSource;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        //https://www.browserling.com/tools/bcrypt
        return new BCryptPasswordEncoder(6);
        //return NoOpPasswordEncoder.getInstance();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringJwtAuthJdbcApplication.class, args);
    }
}
