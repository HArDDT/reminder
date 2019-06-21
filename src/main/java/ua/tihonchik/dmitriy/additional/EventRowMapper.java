package ua.tihonchik.dmitriy.additional;

import org.springframework.jdbc.core.RowMapper;
import ua.tihonchik.dmitriy.entities.Event;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventRowMapper implements RowMapper<Event> {

    @Override
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Event(rs.getInt("id"),
                rs.getInt("userid"),
                rs.getString("description"),
                rs.getTimestamp("eventdate").toLocalDateTime(),
                rs.getBoolean("activeevent"),
                rs.getString("reminderexpression"));
    }
}
