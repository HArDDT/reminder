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
import ua.tihonchik.dmitriy.entities.UserDetailsCustom;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserRepositoryImpl implements UserRepository {

    private JdbcTemplate template;
    private Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    public UserRepositoryImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String sqlQuery = "SELECT id, email, name, admin, superadmin, password FROM public.users where email = ?";

        Object[] eventFields = {username};

        try {
            return template.queryForObject(sqlQuery, eventFields,
                    (ResultSet resultSet, int rowNum) -> new UserDetailsCustom(resultSet.getInt("id"),
                            resultSet.getString("email"),
                            resultSet.getString("name"),
                            resultSet.getString("password"),(
                            getRolesFromBoolean(resultSet.getBoolean("admin"), resultSet.getBoolean("superadmin")))
                    ));
        } catch (EmptyResultDataAccessException exception) {
            String errorMessage = "The user with login - " + username + " not found!";
            logger.error(errorMessage);
            throw new UsernameNotFoundException(errorMessage);
        }

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
