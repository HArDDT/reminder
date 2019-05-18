package ua.tihonchik.dmitriy.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ua.tihonchik.dmitriy.entities.User;
import ua.tihonchik.dmitriy.repositories.UserRepository;

import java.util.Collection;

@Component
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.loadUserByUsername(username);
    }

    @Override
    public int createUser(User user) {
        return repository.createUser(user);
    }

    @Override
    public User getUserById(int id) {
        return repository.getUserById(id);
    }

    @Override
    public void updateUser(User user) {
        repository.updateUser(user);
    }

    @Override
    public void deleteUser(int id) {
        repository.deleteUser(id);
    }

    @Override
    public Collection<User> getUsers(int id) {
        return repository.getUsers(id);
    }
}
