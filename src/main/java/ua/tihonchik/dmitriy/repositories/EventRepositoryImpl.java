package ua.tihonchik.dmitriy.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.tihonchik.dmitriy.additional.EventRowMapper;
import ua.tihonchik.dmitriy.entities.Event;
import ua.tihonchik.dmitriy.exceptions.EventCreationException;
import ua.tihonchik.dmitriy.exceptions.EventDeleteException;
import ua.tihonchik.dmitriy.exceptions.EventNotFoundException;

import java.sql.Types;
import java.util.Collection;

@Repository
public class EventRepositoryImpl implements EventRepository {

    private JdbcTemplate template;
    private Logger logger = LoggerFactory.getLogger(EventRepositoryImpl.class);
    private RowMapper<Event> rowMapper;

    public EventRepositoryImpl(JdbcTemplate template, EventRowMapper rowMapper) {
        this.template = template;
        this.rowMapper = rowMapper;
    }

    @Override
    public int createEvent(Event event) {

        String sqlQuery = "insert into public.events(userid, description, eventdate, activeevent, reminderexpression) " +
                "values (?, ?, ?, ?, ? )" +
                "RETURNING id";

        Object[] eventFields = {event.getUserId(),
                event.getDescription(),
                event.getEventDate(),
                event.isActiveEvent(),
                event.getReminderExpression()};
        try {
            return template.queryForObject(sqlQuery, Integer.class, eventFields);
        } catch (Exception exception) {
            String errorMessage = "User with id - " + event.getUserId() + " not found!";
            logger.error(errorMessage);
            throw new EventCreationException(errorMessage, exception);
        }

    }

    @Override
    public Collection<Event> getEvents(int userId) {

        String sqlQuery = "select id, userid, description, eventdate, activeevent, reminderexpression from public.events where userid = ?";

        Object[] eventFields = {userId};

        return template.query(sqlQuery, eventFields, rowMapper);

    }

    @Override
    public Event getEvent(int eventId) {

        String sqlQuery = "select id, userid, description, eventdate, activeevent, reminderexpression from public.events where id = ?";

        Object[] eventFields = {eventId};

        try {
            return template.queryForObject(sqlQuery, eventFields, rowMapper);
        } catch (EmptyResultDataAccessException exception) {
            String errorMessage = "The event with id - " + eventId + " not found!";
            logger.error(errorMessage);
            throw new EventNotFoundException(errorMessage);
        }

    }

    @Override
    public void updateEvent(Event event) {

        String sqlQuery = "update public.events set description = ?, eventdate = ?, activeevent = ?, reminderexpression = ? where id = ?";

        Object[] eventFields = {event.getDescription(),
                event.getEventDate(),
                event.isActiveEvent(),
                event.getReminderExpression(),
                event.getId()};

        int[] argTypes = {Types.VARCHAR, Types.TIMESTAMP, Types.BOOLEAN, Types.VARCHAR, Types.INTEGER};

        int countOfRow = template.update(sqlQuery, eventFields, argTypes);

        if (countOfRow == 0) {
            String errorMessage = "Event update failed: event with id - " + event.getId() + ", not found!";
            logger.error(errorMessage);
            throw new EventCreationException(errorMessage);
        }

    }

    @Override
    public void deleteEvent(int eventId) {

        String sqlQuery = "delete from public.events where id = ?";

        Object[] eventFields = {eventId};

        int[] argTypes = {Types.INTEGER};

        int countOfRow = template.update(sqlQuery, eventFields, argTypes);

        if (countOfRow == 0) {
            String errorMessage = "Error deleting event: event with id - " + eventId + " not found!";
            logger.error(errorMessage);
            throw new EventDeleteException(errorMessage);
        }

    }
}
