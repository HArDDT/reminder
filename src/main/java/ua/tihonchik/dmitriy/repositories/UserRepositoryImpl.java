package ua.tihonchik.dmitriy.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.tihonchik.dmitriy.additional.UserRowMapper;
import ua.tihonchik.dmitriy.entities.User;
import ua.tihonchik.dmitriy.entities.UserImpl;

import java.sql.Types;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRepositoryImpl implements UserRepository {

    private JdbcTemplate template;
    private PasswordEncoder encoder;
    private Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private RowMapper<UserImpl> userRowMapper;

    public UserRepositoryImpl(JdbcTemplate template, PasswordEncoder encoder, UserRowMapper userRowMapper) {
        this.template = template;
        this.encoder = encoder;
        this.userRowMapper = userRowMapper;
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
            return template.queryForObject(sqlQuery, eventFields, userRowMapper);
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
            return template.queryForObject(sqlQuery, eventFields, userRowMapper);
        } catch (EmptyResultDataAccessException exception) {
            String errorMessage = "The user with email - " + email + " not found!";
            logger.error(errorMessage);
            throw new UsernameNotFoundException(errorMessage);
        }

    }

    @Override
    public void deleteUser(int id) {

        String sqlQuery = "delete from public.users where id = ?;";

        Object[] fields = {id};
        int[] argTypes = {Types.INTEGER};
        int countOfRow = template.update(sqlQuery, fields, argTypes);

        if (countOfRow == 0) {
            String errorMessage = "Deletion error: dashboard with: id - " + id + ", not found!";
            logger.error(errorMessage);
            throw new UsernameNotFoundException(errorMessage);
        }

    }

    @Override
    public List<User> getUsers() {
        String sqlQuery = "select id, email, name, admin, superadmin, password from public.users;";
        return template.query(sqlQuery, userRowMapper).stream().collect(Collectors.toList());
    }

}
