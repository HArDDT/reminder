package ua.tihonchik.dmitriy.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
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
    public Object createEvent(Event event) {

        String sqlQuery = "insert into public.events(id, userid, description, eventdate, activeevent, reminderexpression) " +
                "values (?, ?, ?, ?, ?, ? )" +
                "RETURNING id";

        Object[] eventFields = {
                event.getId().toString(),
                event.getUserId().toString(),
                event.getDescription(),
                event.getEventDate(),
                event.isActiveEvent(),
                event.getReminderExpression()};
        try {
            return template.queryForObject(sqlQuery, String.class, eventFields);
        } catch (Exception exception) {
            String errorMessage = "User with id - " + event.getUserId() + " not found!";
            logger.error(errorMessage);
            throw new EventCreationException(errorMessage, exception);
        }

    }

    @Override
    public Collection<Event> getEvents(Object userId) {

        String sqlQuery = "select id, userid, description, eventdate, activeevent, reminderexpression from public.events where userid = ?";

        Object[] eventFields = {userId.toString()};

        return template.query(sqlQuery, eventFields, rowMapper);

    }

    @Override
    public Event getEvent(Object eventId) {

        String sqlQuery = "select id, userid, description, eventdate, activeevent, reminderexpression from public.events where id = ?";

        Object[] eventFields = {eventId.toString()};

        try {
            return template.queryForObject(sqlQuery, eventFields, rowMapper);
        } catch (EmptyResultDataAccessException exception) {
            String errorMessage = "The event with id - " + eventId.toString() + " not found!";
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
                event.getId().toString()};

        int[] argTypes = {Types.VARCHAR, Types.TIMESTAMP, Types.BOOLEAN, Types.VARCHAR, Types.VARCHAR};

        int countOfRow;

        try {
            countOfRow = template.update(sqlQuery, eventFields, argTypes);
        } catch (Exception ex) {
            logger.error("incorrect data!");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorrect data!");
        }

        if (countOfRow == 0) {
            String errorMessage = "Event update failed: event with id - " + event.getId() + ", not found!";
            logger.error(errorMessage);
            throw new EventCreationException(errorMessage);
        }

    }

    @Override
    public void deleteEvent(Object eventId) {

        String sqlQuery = "delete from public.events where id = ?";

        Object[] eventFields = {eventId.toString()};

        int[] argTypes = {Types.VARCHAR};

        int countOfRow = template.update(sqlQuery, eventFields, argTypes);

        if (countOfRow == 0) {
            String errorMessage = "Error deleting event: event with id - " + eventId.toString() + " not found!";
            logger.error(errorMessage);
            throw new EventDeleteException(errorMessage);
        }

    }
}
