package ua.tihiy.reminder.users;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public int createUser(User user) {
        return repository.createUser(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return repository.getUserByEmail(email);
    }

    @Override
    public Optional<User> getUserById(int id) {
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
