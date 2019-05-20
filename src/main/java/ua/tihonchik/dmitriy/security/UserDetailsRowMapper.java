package ua.tihonchik.dmitriy.security;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserDetailsRowMapper implements RowMapper {

    private CustomUserDetails userDetails;

    public UserDetailsRowMapper(CustomUserDetails userDetails) {
        this.userDetails = userDetails;
    }

    @Override
    public UserDetails mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        if (resultSet.getBoolean("admin")){
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }

        if (resultSet.getBoolean("superadmin")){
            authorities.add(new SimpleGrantedAuthority("SUPER_ADMIN"));
        }

        userDetails.setId(resultSet.getInt("id"));
        userDetails.setEmail(resultSet.getString("email"));
        userDetails.setName(resultSet.getString("name"));
        userDetails.setEnabled(true);
        userDetails.setPassword(resultSet.getString("password"));
        userDetails.setRoles(authorities);

        return userDetails;

    }
}
