package ua.tihiy.reminder.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.tihiy.reminder.entities.rowmappers.UserRowMapper;
import ua.tihiy.reminder.entities.User;

import javax.validation.constraints.NotNull;
import java.sql.Types;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@PropertySource("classpath:properties/sql/queries.properties")
public class UserRepositoryImpl implements UserRepository {

    private JdbcTemplate template;
    private PasswordEncoder encoder;
    private Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private Environment environment;

    public UserRepositoryImpl(JdbcTemplate template, PasswordEncoder encoder, Environment environment) {
        this.template = template;
        this.encoder = encoder;
        this.environment = environment;
    }

    @Override
    public int createUser(User user) {

        String sqlQuery = environment.getProperty("user.create");

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
        String sqlQuery = environment.getProperty("user.get.by.id");
        try {
            return Optional.of(template.queryForObject(sqlQuery, new Object[] {id}, new UserRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            String errorMessage = "The user with id - " + id + " not found!";
            logger.error(errorMessage);
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserByEmail(@NotNull String email) {
        String sqlQuery = environment.getProperty("user.get.by.email");
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
        String sqlQuery = environment.getProperty("user.delete");
        int countOfRow = template.update(sqlQuery, new Object[] {id}, new int[] {Types.INTEGER});
        if (countOfRow == 0) {
            String errorMessage = "User with id - " + id + ", not found!";
            logger.error(errorMessage);
            throw new UsernameNotFoundException(errorMessage);
        }
    }

    @Override
    public List<User> getUsers() {
        String sqlQuery = environment.getProperty("user.get.all");
        return template.query(sqlQuery, new UserRowMapper()).stream().collect(Collectors.toList());
    }

}
