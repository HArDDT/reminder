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

import java.sql.Types;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            String errorMessage = "User: " + user.getEmail() + " not created!";
            logger.error(errorMessage, exception);
            throw exception;
        }
    }

    @Override
    public Optional<User> getUserById(int id) {

        String sqlQuery = "select id, email, name, admin, superadmin, password " +
                "from public.users where id = ?";

        try {
            return Optional.of(template.queryForObject(sqlQuery, new Object[] {id}, new UserRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            String errorMessage = "The user with id - " + id + " not found!";
            logger.error(errorMessage);
            return Optional.empty();
        }

    }

    @Override
    public Optional<User> getUserByEmail(String email) {

        String sqlQuery = "select id, email, name, admin, superadmin, password " +
                "from public.users where email = ?";

        try {
            return Optional.of(template.queryForObject(sqlQuery, new Object[] {email}, new UserRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            String errorMessage = "The user with email - " + email + " not found!";
            logger.error(errorMessage);
            return Optional.empty();
        }

    }

    @Override
    public void deleteUser(int id) {

        String sqlQuery = "delete from public.users where id = ?;";

        int countOfRow = template.update(sqlQuery, new Object[] {id}, new int[] {Types.INTEGER});

        if (countOfRow == 0) {
            String errorMessage = "User with id - " + id + ", not found!";
            logger.error(errorMessage);
            throw new UsernameNotFoundException(errorMessage);
        }

    }

    @Override
    public List<User> getUsers() {
        String sqlQuery = "select id, email, name, admin, superadmin, password from public.users;";
        return template.query(sqlQuery, new UserRowMapper()).stream().collect(Collectors.toList());
    }

}
