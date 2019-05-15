package ua.tihonchik.dmitriy.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.tihonchik.dmitriy.entities.Event;
import ua.tihonchik.dmitriy.entities.EventImpl;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Repository
public class EventRepositoryImpl implements EventRepository {

    private JdbcTemplate template;

    public EventRepositoryImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public int createEvent(Event event) {

        String sqlQuery = "INSERT INTO public.events(userid, description, eventdate, activeevent, reminderexpression)" +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";

        Object[] eventFields = List.of(event.getUserId(),
                event.getDescription(),
                event.getEventDate(),
                event.isActiveEvent(),
                event.getReminderExpression()).toArray();

        return template.queryForObject(sqlQuery, Integer.class, eventFields);

    }

    @Override
    public Collection<Event> getEvents(int userId) {

        String sqlQuery = "SELECT id, userid, description, eventdate, activeevent, reminderexpression FROM public.events where userid = ?";

        Object[] eventFields = List.of(userId).toArray();

        return template.query(sqlQuery, eventFields,
                (ResultSet resultSet, int rowNum) -> new EventImpl(resultSet.getInt("id"),
                        resultSet.getInt("userid"),
                        resultSet.getString("description"),
                        LocalDateTime.parse(resultSet.getString("eventdate"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        resultSet.getBoolean("activeevent"),
                        resultSet.getString("reminderexpression")));

    }

}
