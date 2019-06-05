package ua.tihonchik.dmitriy.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.tihonchik.dmitriy.additional.UserRowMapper;
import ua.tihonchik.dmitriy.entities.User;

import java.util.Collection;

@Component
public class UserRepositoryImpl implements UserRepository {

    private JdbcTemplate template;
    private PasswordEncoder encoder;

    private Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    public UserRepositoryImpl(JdbcTemplate template, PasswordEncoder encoder) {
        this.template = template;
        this.encoder = encoder;
    }

    @Override
    public int createUser(User user) {

        String sqlQuery = "insert into public.users( " +
                "email, name, admin, superadmin, password) " +
                "values (?, ?, ?, ?, ?)" +
                "returning id";

        Object[] userFields = {
                user.getEmail(),
                user.getName(),
                user.hasRole("admin"),
                user.hasRole("super_admin"),
                encoder.encode(user.getPassword())
        };

        try {
            return template.queryForObject(sqlQuery, Integer.class, userFields);
        } catch (EmptyResultDataAccessException exception) {
            String errorMessage = "UserImpl: " + user.getEmail() + " not created!";
            logger.error(errorMessage, exception);
            throw exception;
        }
    }

    @Override
    public User getUserById(int id) {

        String sqlQuery = "select id, email, name, admin, superadmin, password " +
                "from public.users where id = ?";

        Object[] eventFields = {id};

        try {
            return template.queryForObject(sqlQuery, eventFields, new UserRowMapper());
        } catch (EmptyResultDataAccessException exception) {
            String errorMessage = "The user with id - " + id + " not found!";
            logger.error(errorMessage);
            throw new UsernameNotFoundException(errorMessage);
        }

    }

    @Override
    public User getUserByEmail(String email) {

        String sqlQuery = "select id, email, name, admin, superadmin, password " +
                "from public.users where email = ?";

        Object[] eventFields = {email};

        try {
            return template.queryForObject(sqlQuery, eventFields, new UserRowMapper());
        } catch (EmptyResultDataAccessException exception) {
            String errorMessage = "The user with email - " + email + " not found!";
            logger.error(errorMessage);
            throw new UsernameNotFoundException(errorMessage);
        }

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public Collection<User> getUsers() {
        return null;
    }

}
