package ua.tihonchik.dmitriy.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ua.tihonchik.dmitriy.entities.User;
import ua.tihonchik.dmitriy.repositories.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@Component
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public int createUser(User user) {
        user.setRoles(new HashSet<GrantedAuthority>(Collections.singleton(new SimpleGrantedAuthority("USER"))));
        return repository.createUser(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return repository.getUserByEmail(email);
    }

    @Override
    public User getUserById(int id) {
        return repository.getUserById(id);
    }

    @Override
    public void deleteUser(int id) {
        repository.deleteUser(id);
    }

    @Override
    public Collection<User> getUsers() {
        return repository.getUsers();
    }


}
