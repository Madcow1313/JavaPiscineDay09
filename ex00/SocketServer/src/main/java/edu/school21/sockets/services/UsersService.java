package edu.school21.sockets.services;

import edu.school21.sockets.models.User;

public interface UsersService {
    User createNewUser(String name, String password);
    void saveUser(User user);
}
