package ua.tihonchik.dmitriy.services;

import ua.tihonchik.dmitriy.entities.User;

import java.util.Collection;

public interface UserService {

    int createUser(User user);

    User getUserById(int id);

    void updateUser(User user);

    void deleteUser(int id);

    Collection<User> getUsers(int id);

}
