package ua.tihonchik.dmitriy.services;

import ua.tihonchik.dmitriy.entities.User;
import ua.tihonchik.dmitriy.entities.UserImpl;

import java.util.Collection;

public interface UserService {

    int createUser(User user);

    User getUserById(int id);

    User getUserByEmail(String email);

    void updateUser(User user);

    void deleteUser(int id);

    Collection<User> getUsers();

}
