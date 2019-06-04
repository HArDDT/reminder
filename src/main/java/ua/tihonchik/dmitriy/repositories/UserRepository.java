package ua.tihonchik.dmitriy.repositories;

import ua.tihonchik.dmitriy.entities.User;

import java.util.Collection;

public interface UserRepository {

    int createUser(User user);

    User getUserById(int id);

    User getUserByEmail(String email);

    void updateUser(User user);

    void deleteUser(int id);

    Collection<User> getUsers();

}
