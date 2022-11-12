package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    User createNewUser(String name, String password);
    void saveUser(User user);
    Optional<User> findUserByName(String name);
    List<User> findAll();
    PasswordEncoder getPasswordEncoder();
}
