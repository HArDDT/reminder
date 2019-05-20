package ua.tihonchik.dmitriy.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ua.tihonchik.dmitriy.entities.User;
import ua.tihonchik.dmitriy.entities.UserImpl;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserRepositoryImpl implements UserRepository {

    private JdbcTemplate template;
    private UserDetails userDetails;
    private Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    public UserRepositoryImpl(JdbcTemplate template, UserDetails userDetails) {
        this.template = template;
        this.userDetails = userDetails;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String sqlQuery = "SELECT id, email, name, admin, superadmin, password FROM public.users where email = ?";

        Object[] eventFields = {username};

        try {
            UserImpl user = template.queryForObject(sqlQuery, eventFields,
                    (ResultSet resultSet, int rowNum) -> new UserImpl(
                            resultSet.getInt("id"),
                            resultSet.getString("email"),
                            resultSet.getString("name"),
                            resultSet.getString("password"),
                            resultSet.getBoolean("admin"),
                            resultSet.getBoolean("admin"),
                            resultSet.getBoolean("superadmin"))
            );
            return getUserDetailsFromUser(user);
        } catch (EmptyResultDataAccessException exception) {
            String errorMessage = "The user with login - " + username + " not found!";
            logger.error(errorMessage);
            throw new UsernameNotFoundException(errorMessage);
        }

    }

    private UserDetails getUserDetailsFromUser(User user) {
        //userDetails.
        return null;
    }

    @Override
    public int createUser(User user) {

        String sqlQuery = "INSERT INTO public.users( " +
                "email, name, admin, superadmin, password) " +
                "VALUES (?, ?, ?, ?, ?)" +
                "RETURNING id";

        Object[] userFields = {
                user.getUsername(),
                user.getName(),
                user.hasRole("admin"),
                user.hasRole("super_admin"),
                user.getPassword()
        };

        try {
            return template.queryForObject(sqlQuery, Integer.class, userFields);
        } catch (EmptyResultDataAccessException exception) {
            String errorMessage = "User: " + user.getUsername() + " not created!";
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

    Set<GrantedAuthority> getRolesFromBoolean(boolean ... booleans){
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority("USER"));
        if (booleans.length>=1 && booleans[0]){
            roles.add(new SimpleGrantedAuthority("ADMIN"));
        }
        if (booleans.length>=2 && booleans[1]){
            roles.add(new SimpleGrantedAuthority("SUPER_ADMIN"));
        }
        return roles;
    }

}
