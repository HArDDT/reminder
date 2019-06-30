package ua.tihiy.reminder.users;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import ua.tihiy.reminder.users.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        Set<String> roles = new HashSet<>(Collections.singleton("USER"));

        if(rs.getBoolean("admin")){
            roles.add("ADMIN");
        }

        if(rs.getBoolean("superadmin")){
            roles.add("SUPER_ADMIN");
        }

        return new User(rs.getInt("id"),
                rs.getString("email"),
                rs.getString("name"),
                rs.getString("password"),
                roles);
    }

    public static User getUser(SqlRowSet rs) {

        Set<String> roles = new HashSet<>(Collections.singleton("USER"));

        if(rs.getBoolean("admin")){
            roles.add("ADMIN");
        }

        if(rs.getBoolean("superadmin")){
            roles.add("SUPER_ADMIN");
        }

        return new User(rs.getInt("id"),
                rs.getString("email"),
                rs.getString("name"),
                rs.getString("password"),
                roles);
    }


}
