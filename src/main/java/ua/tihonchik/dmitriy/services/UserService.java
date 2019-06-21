package ua.tihonchik.dmitriy.services;

import ua.tihonchik.dmitriy.entities.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    Object createUser(User user);

    Optional<User> getUserById(int id);

    Optional<User> getUserByEmail(String email);

    void deleteUser(int id);

    Collection<User> getUsers();

}
