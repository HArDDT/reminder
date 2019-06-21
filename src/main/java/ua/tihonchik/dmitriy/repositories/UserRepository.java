package ua.tihonchik.dmitriy.repositories;

import ua.tihonchik.dmitriy.entities.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    Object createUser(User user);

    Optional<User> getUserById(int id);

    Optional<User> getUserByEmail(String email);

    void deleteUser(int id);

    Collection<User> getUsers();

}
