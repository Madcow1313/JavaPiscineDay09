package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
    final UsersRepositoryImpl repository;
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public UsersServiceImpl(UsersRepositoryImpl repository){
        this.repository = repository;
    }

    @Override
    public User createNewUser(String name, String password) {
        User user = new User(name, password);

        user.setPassword(encoder().encode(password));
        return user;
    }

    @Override
    public void saveUser(User user){
        repository.save(user);
    }

    @Override
    public Optional<User> findUserByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public PasswordEncoder getPasswordEncoder() {
        return encoder();
    }

    @Override
    public List<User> findAll(){
        return repository.findAll();
    }
}
