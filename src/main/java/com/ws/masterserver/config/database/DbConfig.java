package com.ws.masterserver.config.database;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DbConfig {

//    @Value(value = "${spring.datasource.username}")
//    private String username;
//    @Value(value = "${spring.datasource.password}")
//    private String password;
//    @Value(value = "${spring.datasource.url}")
//    private String url;
    private String className = "org.postgresql.Driver";

    @Bean
    public DataSource dataSource() {
//      String username = "sbxxefukpqiawk";
//      String password = "a8a6d9fc3b3acb03cc2b10c9a43dd0b345f0041c6ff62f2d039858f9360001d5";
//      String url = "jdbc:postgresql://ec2-52-214-23-110.eu-west-1.compute.amazonaws.com:5432/d2n0ml1pik72j4?sslmode=require";

        String username = "postgres";
        String password = "123456";
        String url = "jdbc:postgresql://localhost:5432/datn_v1";


      String className = "org.postgresql.Driver";

      DataSourceBuilder dataSource = DataSourceBuilder.create();
      dataSource.driverClassName(className);
      dataSource.username(username);
      dataSource.password(password);
      dataSource.url(url);
      return dataSource.build();
    }
}
