package ua.tihonchik.dmitriy.additional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ua.tihonchik.dmitriy.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        Set roles = new HashSet<>(Collections.singleton(new SimpleGrantedAuthority("USER")));

        if(rs.getBoolean("admin")){
            roles.add(new SimpleGrantedAuthority("ADMIN"));
        }

        if(rs.getBoolean("superadmin")){
            roles.add(new SimpleGrantedAuthority("SUPER_ADMIN"));
        }

        return new User(rs.getInt("id"),
                rs.getString("email"),
                rs.getString("name"),
                rs.getString("password"),
                roles);
    }

}
