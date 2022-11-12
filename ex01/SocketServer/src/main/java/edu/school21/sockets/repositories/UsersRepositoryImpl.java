package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UsersRepositoryImpl implements UsersRepository {
    private JdbcTemplate jdbcTemplate;
    private class MyRowMapper implements RowMapper<User>{

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(rs.getString(2), rs.getString(3));
            return user;
        }
    }
    @Autowired
    public UsersRepositoryImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        init();
    }

    void init(){
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS server");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS server.user (id serial primary key," +
                " name varchar(255) not null unique," + " password varchar(255) not null);");
        System.out.println("Table created");
    }
    @Override
    public User findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM server.user WHERE id = ?", new MyRowMapper(), id);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM server.user", new MyRowMapper());
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update("INSERT INTO server.user (name, password) VALUES (?, ?)", entity.getName(), entity.getPassword());
        entity.setId(findByName(entity.getName()).get().getId());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update("UPDATE server.user SET name = ? WHERE id = ?",
                entity.getName(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM server.user WHERE id = ?", id);
    }

    @Override
    public Optional<User> findByName(String name) {
       try {
           return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM server.user WHERE name = ?",
                   new MyRowMapper(), name));
       }
       catch (Exception e){}
       return Optional.empty();
    }
}
