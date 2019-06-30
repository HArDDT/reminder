package ua.tihiy.reminder.events.repeatable;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RepeatableEventRowMapper implements RowMapper<RepeatableEvent> {

    @Override
    public RepeatableEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RepeatableEvent(rs.getInt("id"),
                rs.getInt("userid"),
                rs.getString("description"),
                rs.getTimestamp("eventdate").toLocalDateTime(),
                rs.getBoolean("activeevent"),
                rs.getString("reminderexpression"));
    }

    public static RepeatableEvent getEvent(SqlRowSet rs) {
        return new RepeatableEvent(rs.getInt("id"),
                rs.getInt("userid"),
                rs.getString("description"),
                rs.getTimestamp("eventdate").toLocalDateTime(),
                rs.getBoolean("activeevent"),
                rs.getString("reminderexpression"));
    }

}
