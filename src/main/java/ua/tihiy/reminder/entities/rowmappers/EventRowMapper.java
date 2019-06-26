package ua.tihiy.reminder.entities.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import ua.tihiy.reminder.entities.Event;

import javax.sql.RowSet;
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

    public static Event getEvent(SqlRowSet rs) {
        return new Event(rs.getInt("id"),
                rs.getInt("userid"),
                rs.getString("description"),
                rs.getTimestamp("eventdate").toLocalDateTime(),
                rs.getBoolean("activeevent"),
                rs.getString("reminderexpression"));
    }

}
