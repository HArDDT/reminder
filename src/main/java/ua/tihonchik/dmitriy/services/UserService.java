package ua.tihonchik.dmitriy.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ua.tihonchik.dmitriy.entities.User;

import java.util.Collection;

public interface UserService extends UserDetailsService {

    int createUser(User user);

    User getUserById(int id);

    User getUserPrototypeBean();

    void updateUser(User user);

    void deleteUser(int id);

    Collection<User> getUsers(int id);

}
