package ua.tihonchik.dmitriy.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ua.tihonchik.dmitriy.entities.User;

import java.util.Collection;

@Component
public class UserRepositoryImpl implements UserRepository {

    private JdbcTemplate template;
    private UserDetails userDetails;
    private RowMapper<UserDetails> rw;

    private Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    public UserRepositoryImpl(JdbcTemplate template, UserDetails userDetails, RowMapper<UserDetails> rw) {
        this.template = template;
        this.userDetails = userDetails;
        this.rw = rw;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String sqlQuery = "SELECT id, email, name, admin, superadmin, password FROM public.users where email = ?";

        Object[] eventFields = {username};

        try {
            return template.queryForObject(sqlQuery, eventFields, rw);
        } catch (EmptyResultDataAccessException exception) {
            String errorMessage = "The user with login - " + username + " not found!";
            logger.error(errorMessage);
            throw new UsernameNotFoundException(errorMessage);
        }

    }

    @Override
    public int createUser(User user) {

        String sqlQuery = "INSERT INTO public.users( " +
                "email, name, admin, superadmin, password) " +
                "VALUES (?, ?, ?, ?, ?)" +
                "RETURNING id";

        Object[] userFields = {
                user.getEmail(),
                user.getName(),
                user.hasRole("admin"),
                user.hasRole("super_admin"),
                user.getPassword()
        };

        try {
            return template.queryForObject(sqlQuery, Integer.class, userFields);
        } catch (EmptyResultDataAccessException exception) {
            String errorMessage = "User: " + user.getEmail() + " not created!";
            logger.error(errorMessage, exception);
            throw exception;
        }
    }

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public Collection<User> getUsers(int id) {
        return null;
    }

}
