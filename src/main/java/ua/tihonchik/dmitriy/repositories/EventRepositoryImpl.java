package ua.tihonchik.dmitriy.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.tihonchik.dmitriy.entities.Event;
import ua.tihonchik.dmitriy.entities.EventImpl;
import ua.tihonchik.dmitriy.exceptions.EventCreationException;
import ua.tihonchik.dmitriy.exceptions.EventDeleteException;
import ua.tihonchik.dmitriy.exceptions.EventNotFoundException;

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

        String sqlQuery = "INSERT INTO public.events(userid, description, eventdate, activeevent, reminderexpression) " +
                "(select ?, ?, ?, ?, ? " +
                "where exists (select * from public.users where id = ?))" +
                "RETURNING id";

        Object[] eventFields = {event.getUserId(),
                event.getDescription(),
                event.getEventDate(),
                event.isActiveEvent(),
                event.getReminderExpression(),
                event.getUserId()};
        try {
            return template.queryForObject(sqlQuery, Integer.class, eventFields);
        } catch (EmptyResultDataAccessException ex) {
            throw new EventCreationException("User with id - " + event.getUserId() + " not found!");
        }

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

        try {
            return template.queryForObject(sqlQuery, eventFields,
                    (ResultSet resultSet, int rowNum) -> new EventImpl(resultSet.getInt("id"),
                            resultSet.getInt("userid"),
                            resultSet.getString("description"),
                            LocalDateTime.parse(resultSet.getString("eventdate"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                            resultSet.getBoolean("activeevent"),
                            resultSet.getString("reminderexpression")));
        } catch (EmptyResultDataAccessException ex) {
            throw new EventNotFoundException("The event with: user id - " + eventId + ", event id - " + userId + " not found!");
        }

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

        int countOfRow = template.update(sqlQuery, eventFields, argTypes);

        if (countOfRow == 0) {
            throw new EventCreationException("Event update failed: event with: user id - " + event.getId() + ", event id - " + event.getUserId() + " not found!");
        }

    }

    @Override
    public void deleteEvent(int eventId, int userId) {

        String sqlQuery = "DELETE FROM public.events WHERE id = ? AND userid = ?";

        Object[] eventFields = {eventId, userId};

        int[] argTypes = {
                Types.INTEGER,
                Types.INTEGER};

        int countOfRow = template.update(sqlQuery, eventFields, argTypes);

        if (countOfRow == 0) {
            throw new EventDeleteException("Error deleting event: event with: user id - " + userId + ", event id - " + eventId + " not found!");
        }

    }
}
