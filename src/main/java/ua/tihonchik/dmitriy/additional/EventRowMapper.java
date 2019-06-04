package ua.tihonchik.dmitriy.additional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.tihonchik.dmitriy.entities.Event;
import ua.tihonchik.dmitriy.entities.EventImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class EventRowMapper implements RowMapper<Event> {

    @Override
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EventImpl(rs.getInt("id"),
                rs.getInt("userid"),
                rs.getString("description"),
                LocalDateTime.parse(rs.getString("eventdate"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                rs.getBoolean("activeevent"),
                rs.getString("reminderexpression"));
    }
}
