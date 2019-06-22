package ua.tihiy.reminder.repositories;

import ua.tihiy.reminder.entities.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    int createUser(User user);

    Optional<User> getUserById(int id);

    Optional<User> getUserByEmail(String email);

    void deleteUser(int id);

    Collection<User> getUsers();

}
