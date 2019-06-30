package ua.tihiy.reminder.users;

import ua.tihiy.reminder.users.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    int createUser(User user);

    Optional<User> getUserById(int id);

    Optional<User> getUserByEmail(String email);

    void deleteUser(int id);

    Collection<User> getUsers();

}
