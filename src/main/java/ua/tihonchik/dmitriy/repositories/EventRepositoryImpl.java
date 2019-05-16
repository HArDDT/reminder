package ua.tihonchik.dmitriy.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.tihonchik.dmitriy.entities.Event;
import ua.tihonchik.dmitriy.entities.EventImpl;

import java.sql.ResultSet;
import java.sql.Types;
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

        Object[] eventFields = {event.getUserId(),
                event.getDescription(),
                event.getEventDate(),
                event.isActiveEvent(),
                event.getReminderExpression()};

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

    @Override
    public Event getEvent(int eventId, int userId) {

        String sqlQuery = "SELECT id, userid, description, eventdate, activeevent, reminderexpression FROM public.events where userid = ? and id = ?";

        Object[] eventFields = List.of(userId, eventId).toArray();

        return template.queryForObject(sqlQuery, eventFields,
                (ResultSet resultSet, int rowNum) -> new EventImpl(resultSet.getInt("id"),
                        resultSet.getInt("userid"),
                        resultSet.getString("description"),
                        LocalDateTime.parse(resultSet.getString("eventdate"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        resultSet.getBoolean("activeevent"),
                        resultSet.getString("reminderexpression")));

    }

    @Override
    public void updateEvent(Event event) {

        String sqlQuery = "UPDATE public.events SET description=?, eventdate=?, activeevent=?, reminderexpression=?" +
                " WHERE userid = ? AND id = ?";

        Object[] eventFields = {event.getDescription(),
                event.getEventDate(),
                event.isActiveEvent(),
                event.getReminderExpression(),
                event.getUserId(),
                event.getId()};

        int[] argTypes = {
                Types.VARCHAR,
                Types.TIMESTAMP,
                Types.BOOLEAN,
                Types.VARCHAR,
                Types.INTEGER,
                Types.INTEGER};

        template.update(sqlQuery, eventFields, argTypes);

    }

    @Override
    public void deleteEvent(int eventId, int userId) {

        String sqlQuery = "DELETE FROM public.events WHERE id = ? AND userid = ?";

        Object[] eventFields = {eventId, userId};

        int[] argTypes = {
                Types.INTEGER,
                Types.INTEGER};

        template.update(sqlQuery, eventFields, argTypes);

    }
}