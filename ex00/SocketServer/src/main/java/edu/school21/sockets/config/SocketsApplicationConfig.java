package edu.school21.sockets.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:db.properties")
public class SocketsApplicationConfig {
    private HikariDataSource hikariDataSource;

    @Value("${db.url}") String url;
    @Value("${db.user}") String user;
    @Value("${db.password}") String password;

    public SocketsApplicationConfig(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(password);
        this.hikariDataSource = new HikariDataSource(hikariConfig);
    }
    @Bean
    public HikariDataSource getHikariDataSource() {
        return hikariDataSource;
    }
}
