package ua.tihonchik.dmitriy.repositories;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.tihonchik.dmitriy.entities.User;

import java.util.Collection;

public interface UserRepository {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    int createUser(User user);

    User getUserById(int id);

    void updateUser(User user);

    void deleteUser(int id);

    Collection<User> getUsers(int id);

}
